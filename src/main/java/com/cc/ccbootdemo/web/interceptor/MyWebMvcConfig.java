package com.cc.ccbootdemo.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/1/001 18:22.
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getExceptionInterceptor()).addPathPatterns("/**");
    }
    @Bean
     public ExceptionInterceptor getExceptionInterceptor(){
        return new ExceptionInterceptor();
    }

}
