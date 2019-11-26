package com.kangjun.springframework.test;

import com.kangjun.springframework.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) {    
        ApplicationContext beans=new ClassPathXmlApplicationContext("classpath:test.xml");    
        User user=(User)beans.getBean("user");
        System.out.println("username:"+user.getUserName()+"  "+"email:"+user.getEmail());    
    }    
}