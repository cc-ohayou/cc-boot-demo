package com.qunhe.toilet.facade.domain.common.enums;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/11 10:06.
 */
public enum EnvType {
    SIT("sit", "sit环境"),
    DEV("dev", "dev环境"),
    PROD("prod", "prod环境"),

    ;


    private String value;
    private String label;


    EnvType(String value, String label) {
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
