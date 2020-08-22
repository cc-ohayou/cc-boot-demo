package com.qunhe.toilet.core.common.properties;

import com.qunhe.toilet.facade.domain.common.constants.CommonConstants;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
        this.GLOBAL_DIR=environment.getProperty(CommonConstants.GLOBAL_RESOURCES_DIR);
    }
   /* @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        props.setProperty("GLOBAL_DIR",GLOBAL_DIR);
        super.processProperties(beanFactoryToProcess,props);
    }*/
}
