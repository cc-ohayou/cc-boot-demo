package com.cc.ccbootdemo.facade.domain.common.enums;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/10 10:46.
 */
public enum OperUrl {

    ETF_TASK_DEFER("v1/support/defer/manual", "etf递延用url"),
    ETF_TASK_FORCE_SELL("v1/support/forceSell/manual", "etf递延用url"),

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
