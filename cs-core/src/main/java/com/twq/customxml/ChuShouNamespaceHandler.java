package com.twq.customxml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ChuShouNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("api" , new ApiDefinitionParser());
        this.registerBeanDefinitionParser("unit", new UnitDefinitionParser());
        this.registerBeanDefinitionParser("cond", new CondtionDefinitionParser());
    }
}