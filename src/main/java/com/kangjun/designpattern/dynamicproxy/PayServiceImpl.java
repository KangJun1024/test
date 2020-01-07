package com.kangjun.designpattern.dynamicproxy;

import java.math.BigDecimal;

public class PayServiceImpl implements PayService {
    @Override
    public void pay(String username, BigDecimal money) {
        System.out.println(username + "支付" + money + "元!");
    }
}
