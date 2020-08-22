package com.qunhe.toilet.core.common.properties.resource;

import org.springframework.stereotype.Component;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/30/030 22:16.
 */
@Component
/*
只能加载servletContext classpath下的资源配置文件
@PropertySource({"classpath:com/hry/spring/configinject/config.properties",
        "classpath:com/hry/spring/configinject/config_${anotherfile.configinject}.properties"})
*/
public class BaseResourceProperties {
    /*@Value("${master.datasource.url}")
    private String masterUrl;
    @Value("${master.datasource.username}")
    private String masterName;
    @Value("${master.datasource.password}")
    private String masterPassword;
    @Value("${master.datasource.driverClassName}")
    private String masterDriver;

    @Value("${cluster.datasource.url}")
    private String clusterUrl;
    @Value("${cluster.datasource.username}")
    private String clusterName;
    @Value("${cluster.datasource.password}")
    private String clusterPassword;
    @Value("${cluster.datasource.driverClassName}")
    private String clusterDriver;*/

  /*  @Value("classpath:com/hry/spring/configinject/config.txt")
    private Resource resourceFile; // 注入文件资源*/


}
