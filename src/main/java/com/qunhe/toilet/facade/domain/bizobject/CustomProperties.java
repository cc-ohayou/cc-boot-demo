package com.qunhe.toilet.facade.domain.bizobject;

import lombok.Data;

import java.util.Map;

/**
     * @author  CF
     * @date   2019/1/7
     * @description  自定义属性 集合体 用于app全局配置的一些公用属性
     *
     */
@Data
public class CustomProperties {
    private String downLoadUrl;
    private String updateSign;
    // dev  sit prod 三个环境  用于切换 请求url
    private String env;
    private String loginBgUrl;
    private String operBizDetailBgUrl;

    private Map otherProperties;




}
