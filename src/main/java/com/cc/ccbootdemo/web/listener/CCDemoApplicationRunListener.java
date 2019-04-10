package com.cc.ccbootdemo.web.listener;

import com.cc.ccbootdemo.core.service.UploadService;
import com.cc.ccbootdemo.core.service.impl.UploadServiceImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/15/015 19:13.
 */
public class CCDemoApplicationRunListener implements SpringApplicationRunListener{
    private final SpringApplication application;

    private final String[] args;
    @Override
    public void starting() {
        System.err.println("demo listener starting");

    }

    public CCDemoApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.err.println("demo listener environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.err.println("demo contextPrepared");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.err.println("demo contextLoaded ");


    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.err.println("demo listene context started");

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.err.println("demo listener running");
        BeanDefinition service=context.getBeanFactory().getBeanDefinition("uploadService");
        System.err.println(service.toString());
        System.err.println(service.getClass());
        UploadService o= (UploadService) context.getBeanFactory().getBean("uploadService");
        System.err.println(o.getClass());
        System.err.println(o);
        System.err.println(o.uploadVideo());
        UploadService serviceTest= (UploadService) context.getBeanFactory().getBean("uploadTest");
        System.err.println(serviceTest.getClass());
        System.err.println(serviceTest);
        System.err.println(serviceTest.uploadVideo());
        //有多个实现时不能使用此种获取方式( getBean(UploadService.class)) )，
        // 同时代码中使用@Resource引用bean时最好指定name 否则默认使用驼峰命名的bean
        //找不到默认的 会尝试通过UploadService类的方式找寻，
        // 多个实现情况下则会报错org.springframework.beans.factory.NoUniqueBeanDefinitionException:

        // bean的动态移除替换见此blog
        // https://www.cnblogs.com/yjmyzz/p/how-to-dynamic-destroy-or-register-bean-to-spring-container.html
//        System.out.println(context.getBeanFactory().getBean(UploadService.class));


    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.err.println("demo listener failed");

    }
}
