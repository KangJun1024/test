package com.kangjun.java8.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Data {
    private static List<PersonModel> list = null;

    static {
        PersonModel wu = new PersonModel("康俊", 18, "男");
        PersonModel zhang = new PersonModel("李四", 19, "男");
        PersonModel wang = new PersonModel("小芬", 20, "女");
        PersonModel zhao = new PersonModel("爱三", 20, "男");
        PersonModel chen = new PersonModel("王二麻子", 21, "男");
        list = Arrays.asList(wu, zhang, wang, zhao, chen);
    }

    public static List<PersonModel> getData() {
        return list;
    }


    public static void main(String[] args) {

        BigDecimal amount = new BigDecimal("0.0");
        BigDecimal amount2 = new BigDecimal("0");

        System.out.println(amount.equals(amount2));

        System.out.println(amount2.equals(BigDecimal.ZERO));

    }
} 