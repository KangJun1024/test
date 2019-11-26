package com.kangjun.springframework.service;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {

        registerBeanDefinitionParser("users", new UserBeanDefinitionParser());
    }

}