package com.kangjun.designpattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  动态代理：
 *  利用反射动态生成代理类对象
 */
public class PayProxy {
    private Object target;

    public PayProxy(Object target) {
        this.target = target;
    }

    public Object getPayProxy(){
        //类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();
        //类实现接口
        Class[] interfaces = target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("发起支付...");

                Object result = method.invoke(target, args);

                System.out.println("支付状态：成功");

                return result;
            }
        });

        return proxy;
    }

    /**
     *  lambda表达式
     * @return
     */
    public Object getPayProxy2(){
        //类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();
        //类实现接口
        Class[] interfaces = target.getClass().getInterfaces();

        Object result = Proxy.newProxyInstance(classLoader, interfaces, (proxy2, method, args) -> {
            System.out.println("发起支付...");

            return method.invoke(target, args);
        });

        return result;
    }


}
