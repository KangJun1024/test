package com.kangjun.designpattern.staticproxy;

import java.math.BigDecimal;

/**
 *  静态代理：
 *  持有代理类的引用
 */
public class PayProxy implements PayService{
    private PayService payService;

    public PayProxy(PayService payService) {
        this.payService = payService;
    }

    @Override
    public void pay(String username, BigDecimal money) {
        System.out.println(username + "发起支付...");

        payService.pay(username,money);

        System.out.println("支付状态：成功");

    }
}
