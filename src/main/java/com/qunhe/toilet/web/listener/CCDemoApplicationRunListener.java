package com.qunhe.toilet.web.listener;

import com.qunhe.toilet.core.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/15/015 19:13.
 */
@Slf4j
public class CCDemoApplicationRunListener implements SpringApplicationRunListener{
    private final SpringApplication application;

    private final String[] args;
    @Override
    public void starting() {
        log.info("demo listener starting");

    }

    public CCDemoApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("demo listener environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("demo contextPrepared");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("demo contextLoaded ");


    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("demo listene context started");

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
       log.info("demo listener running");
        BeanDefinition service=context.getBeanFactory().getBeanDefinition("uploadService");
        log.info(service.toString());
        log.info(String.valueOf(service.getClass()));
        UploadService o= (UploadService) context.getBeanFactory().getBean("uploadService");
        log.info(String.valueOf(o.getClass()));
        log.info(String.valueOf(o));
        log.info(o.uploadVideo());
//        UploadService serviceTest= (UploadService) context.getBeanFactory().getBean("uploadTest");
//        log.info(serviceTest.getClass());
//        log.info(serviceTest);
//        log.info(serviceTest.uploadVideo());
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
        log.info("demo listener failed");

    }
}
