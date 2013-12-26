package com.github.herong.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;

import com.github.herong.spring.remoting.RemoteBeanDefinitionParser;

/**
 * spring remote custom tag handler
 * 
 * @author herong
 * @mail: herong_wskaps@163.com
 *        {@link org.springframework.beans.factory.xml.NamespaceHandler}
 */
public class SpringRemoteNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("remote-export",
				new RemoteBeanDefinitionParser());
	}

}
