package com.cc.ccbootdemo.facade.domain.common.enums.redis;


/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/17 15:14.
 */
public enum RedisKeyEnum {
  DOWNLOAD_URL_APK("cc-str-apk-download-url","apk下载地址",RedisType.STRING,BizType.COMMON)
    ;


    private String value;
    private String label;
    private RedisType redisKeyType;
    private BizType type;

    RedisKeyEnum(String value, String label, RedisType redisKeyType, BizType type) {
        this.value = value;
        this.label = label;
        this.redisKeyType = redisKeyType;
        this.type = type;
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

    public RedisType getRedisKeyType() {
        return redisKeyType;
    }

    public void setRedisKeyType(RedisType redisKeyType) {
        this.redisKeyType = redisKeyType;
    }

    public BizType getType() {
        return type;
    }

    public void setType(BizType type) {
        this.type = type;
    }

}