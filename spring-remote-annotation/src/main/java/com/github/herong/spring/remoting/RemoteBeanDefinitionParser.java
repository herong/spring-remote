package com.github.herong.spring.remoting;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RemoteBeanDefinitionParser implements BeanDefinitionParser {

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition bd = new RootBeanDefinition(RemotingExporter.class);
        bd.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        bd.setSource(parserContext.extractSource(element));
        parserContext.registerBeanComponent(new BeanComponentDefinition(bd, RemotingExporter.class.getName()));
        return null;
    }
}
