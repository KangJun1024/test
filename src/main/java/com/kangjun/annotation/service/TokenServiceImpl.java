package com.kangjun.annotation.service;

import com.kangjun.common.Constant;
import com.kangjun.common.ResponseCode;
import com.kangjun.common.ServerResponse;
import com.kangjun.exception.ServiceException;
import com.kangjun.util.JedisUtil;
import com.kangjun.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.UUID32();
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.Redis.TOKEN_PREFIX).append(str);
        String key = sb.toString();
        jedisUtil.set(key,key,Constant.Redis.EXPIRE_TIME_DAY);

        return ServerResponse.success(key);
    }

    /**
     * 接口幂等性校验
     * @param request
     */
    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if(StringUtils.isBlank(token)) { //请求头header中不存在token
            token = request.getParameter(TOKEN_NAME); //参数parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

    }

}
