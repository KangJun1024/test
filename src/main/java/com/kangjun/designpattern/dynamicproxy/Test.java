package com.kangjun.designpattern.dynamicproxy;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
//        PayProxy payProxy = new PayProxy(new PayServiceImpl());
//        PayService pay = (PayService) payProxy.getPayProxy();
//        pay.pay("kangjun", BigDecimal.TEN);

        PayProxy payProxy = new PayProxy(new PayServiceImpl());
        PayService pay = (PayService) payProxy.getPayProxy2();
        pay.pay("kangjun", BigDecimal.TEN);

    }

}
