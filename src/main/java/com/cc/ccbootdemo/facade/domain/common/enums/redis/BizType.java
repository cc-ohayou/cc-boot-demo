package com.cc.ccbootdemo.facade.domain.common.enums.redis;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/17 15:26.
 */
public enum BizType {

    COMMON("commo", "测试用common"),

    ;


    private String value;
    private String label;


    BizType(String value, String label) {
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
