package com.kangjun.util.string;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 *  String 字符串拼接，什么时候会走 StringBuilder？
 */
public class Demo {
    public final static String date_format_save = "yyyyMMddHHmmss";

    public static void main(String[] args) {
        String str = "111";
        String a = "111";
        String str2 = a + "";
        String str3 = "111" + "";
        //案例1
        System.out.println(str == str2);
        //案例2
        System.out.println(str == str3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(date_format_save);
        LocalDate now =LocalDate.parse("20191022161013",formatter);
        Date date = new Date();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(simpleFormatter.format(date));
        System.out.println(date);
        System.out.println(DateUtils.addMinutes(date,-5));




    }
}
