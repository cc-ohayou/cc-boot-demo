package com.cc.ccbootdemo.core.common.properties.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/1/001 11:01.
 */
@Configuration
public class TestLoadResource   implements EnvironmentAware{
    private static Logger logger= LoggerFactory.getLogger(TestLoadResource.class);
    public static String globalDir;
    public static Properties property;
    public static boolean loadInit;
    @Bean
    public TestLoadResource getResource(){
        TestLoadResource res=new TestLoadResource();
        return res;
    }


    public static synchronized void loadProperties() {

        globalDir=System.getenv("CC_RESOURCE_DIR");
        FileSystemResource resource=new FileSystemResource(globalDir+"/cc_jdbc.properties");
        try {
            Properties p=PropertiesLoaderUtils.loadProperties(resource);
            logger.info("TestLoadResource Properties="+p);
            property=p;
            loadInit=true;
        } catch (IOException e) {
            logger.error("####error during init base property###",e);

        }

    }


    @Override
    public void setEnvironment(Environment environment) {
/*
        globalDir=environment.getProperty("CC_RESOURCE_DIR");
        System.out.println("TestLoadResource globalDir="+globalDir);
        if(StringUtils.isEmpty(globalDir)){
            logger.error("####error during init globalDir property, probably not set###");
        }
        loadProperties();
//        throw new BusinessException("just for test");*/
    }
}
