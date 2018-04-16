package com.cc.ccbootdemo.web.listener;

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

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.err.println("demo listener failed");

    }
}
