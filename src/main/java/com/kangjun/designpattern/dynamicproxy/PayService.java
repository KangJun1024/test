package com.kangjun.designpattern.dynamicproxy;

import java.math.BigDecimal;

public interface PayService {

    void pay(String username, BigDecimal money);
}
