package com.kangjun.interceptor;

import com.kangjun.annotation.AccessLimit;
import com.kangjun.common.Constant;
import com.kangjun.common.ResponseCode;
import com.kangjun.exception.ServiceException;
import com.kangjun.util.IpUtil;
import com.kangjun.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口防刷限流拦截器
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取处理器方法
        Method method = handlerMethod.getMethod();
        //判断是否有限流注解
        boolean present = method.isAnnotationPresent(AccessLimit.class);
        if(present){
            AccessLimit annotation = method.getAnnotation(AccessLimit.class);
            //业务处理
            check(annotation,request);
        }

        return false;
    }

    private void check(AccessLimit annotation, HttpServletRequest request) {
        int maxCount = annotation.maxCount();
        int seconds = annotation.seconds();

        //拼接唯一标识key
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.Redis.ACCESS_LIMIT_PREFIX).append(IpUtil.getIpAddress(request)).append(request.getRequestURI());
        String key = sb.toString();

        Boolean exists = jedisUtil.exists(key);

        if(!exists){
            jedisUtil.set(key,String.valueOf(1),seconds); //第一次访问设置超时时间 && 次数1
        }else {
            Integer count = Integer.valueOf(jedisUtil.get(key));
            if(count < maxCount){ //小于最大访问次数
                Long ttl = jedisUtil.ttl(key);
                if(ttl <= 0){ //key过期，重置key
                    jedisUtil.set(key,String.valueOf(1),seconds);
                }else {
                    jedisUtil.set(key,String.valueOf(++count),seconds); //访问次数+1 操作
                }
            }else {
                //友情提示
                throw new ServiceException(ResponseCode.ACCESS_LIMIT.getMsg());
            }
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
