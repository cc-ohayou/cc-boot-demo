package com.qunhe.toilet.facade.domain.common.test.design.patterns.strategy;

import com.qunhe.toilet.facade.domain.common.util.ClassScaner;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 12:47.
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {
    private static final String HANDLER_PACKAGE="com.qunhe.toilet.facade.domain.common.test.design";
    /**
     * describe:
     * 扫描HandlerType注解的bean初始化HandlerContext将其注册到Spring容器
     * @see HandleType
     * @see HandlerContext
     * @param
     * @author CAI.F
     * @date: 日期:2019/6/11 时间:11:36
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    Map<String,Class> handlerMap   = Maps.newHashMapWithExpectedSize(3);
    ClassScaner.scan(HANDLER_PACKAGE,HandleType.class).forEach(clazz -> {
            String type =((HandleType)clazz.getAnnotation(HandleType.class)).value();
            handlerMap.put(type,clazz);
            });
    HandlerContext context=new HandlerContext(handlerMap);
    configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(),context);
    }
}
