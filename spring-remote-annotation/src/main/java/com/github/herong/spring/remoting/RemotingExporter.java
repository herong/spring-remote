package com.github.herong.spring.remoting;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.stereotype.Service;

public class RemotingExporter implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
        	
            BeanDefinition define = beanFactory.getBeanDefinition(beanName);
            Class<?> cls = null;
            try {
				 cls = Class.forName(define.getBeanClassName(),false,beanFactory.getBeanClassLoader());
			} catch (ClassNotFoundException e) {
				throw new NoSuchBeanDefinitionException("RemotingExporter.postProcessBeanFactory at Loading bean class error!");
			}
            Class<?> serviceInterface = findServiceInterface(cls);
            if (serviceInterface != null) {
            	Object bean = beanFactory.getBean(beanName);
                RemoteExporter remoteExporter = createRemoteExporter(serviceInterface, bean);
                beanFactory.registerSingleton("/" + serviceInterface.getName(), remoteExporter);
            }
        }
    }

    private Class<?> findServiceInterface(Class<?> cls) {
        Class<?> serviceInterface = null;
        if (AnnotationUtils.isAnnotationDeclaredLocally(Service.class, cls)) {
            for (Class<?> interfaceClass : cls.getInterfaces()) {
                if (AnnotationUtils.isAnnotationDeclaredLocally(Remote.class, interfaceClass)) {
                    serviceInterface = interfaceClass;
                }
            }
        }
        return serviceInterface;
    }

    private RemoteExporter createRemoteExporter(Class<?> serviceInterface, Object service) {
        RemoteExporter remoteExporter = null;
        Remote remote = AnnotationUtils.findAnnotation(service.getClass(), Remote.class);
        try {
            remoteExporter = (RemoteExporter) remote.serviceExporter().newInstance();
            remoteExporter.setService(service);
            remoteExporter.setServiceInterface(serviceInterface);
            if (remoteExporter instanceof RmiServiceExporter) {
                ((RmiServiceExporter) remoteExporter).setServiceName(serviceInterface.getName());
            }
            if (remoteExporter instanceof InitializingBean) {
                ((InitializingBean) remoteExporter).afterPropertiesSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteExporter;
    }
}
