package com.cc.ccbootdemo.facade.domain.common.enums;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/10 10:46.
 */
public enum OperUrl {

    ETF_TASK_DEFER("v1/support/defer/manual", "etf递延用url"),
    ETF_TASK_FORCE_SELL("v1/support/forceSell/manual", "etf递延用url"),
    CLEAR_SUB_CONFIG("v1/msg/clear/subConfigMap", "etf清空刷新订阅配置"),
    INVESTOR_WARM_UP("hello/warmUp", "phoenix task预热资金账号"),
    PS_CLEAN_PUSH_CONFIG("v1/support/clean/pushConfig", "phoenix ps清空原有商户个推推送配置"),
    PS_GET_SETTINGS("v1/support/get/settings", "phoenix ps获取全局配置 推送配置消息订阅配置等"),

    ;


    private String value;
    private String label;


    OperUrl(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
