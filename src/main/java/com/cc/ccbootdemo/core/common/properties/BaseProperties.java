package com.cc.ccbootdemo.core.common.properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Properties;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/30/030 22:17.
 */
@Component
public class BaseProperties /*extends PropertyPlaceholderConfigurer*/ implements EnvironmentAware {
    // 注入操作系统属性
    private String GLOBAL_DIR;

    public String getGLOBAL_DIR() {
        return GLOBAL_DIR;
    }

    public void setGLOBAL_DIR(String GLOBAL_DIR) {
        this.GLOBAL_DIR = GLOBAL_DIR;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.GLOBAL_DIR=environment.getProperty("CC_RESOURCE_DIR");
    }
   /* @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        props.setProperty("GLOBAL_DIR",GLOBAL_DIR);
        super.processProperties(beanFactoryToProcess,props);
    }*/
}
