package com.cc.ccbootdemo.core.common.settings;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/10/30 10:06.
 */
public enum SettingsEnum {
    ROCKETMQ_TEST_TAG01("rocketmq-test-tag01","测试用tag","test-tag01" ),
    ROCKETMQ_TEST_TOPIC01("rocketmq-test-topic01","测试用topic","test-topic01"),
    ROCKETMQ_SEND_TIMEOUT("rocketmq-send-timeout","发送消息超时时间 单位ms","1000"),
    ROCKETMQ_PRUDUCER_NAME_SERVER("rocketmq-producer-name-server","生产者mq服务地址","127.0.0.1:9876"),
    ROCKETMQ_CONSUMER_NAME_SERVER("rocketmq-consumer-name-server","消费者mq服务地址","127.0.0.1:9876"),
    ;
    private String value;
    private String label;
    //默认值必须以线上的作为标准 即便redis挂掉也不要影响正常使用
    private String defaultValue;


    public static void initWork(Map<String,String> settings) {
        checkSettingKeysRepeatCondition();
        if(CollectionUtils.isEmpty(settings)){
            initDefaultSettingHolderValue();
        }else{
            initDefaultSettingHolderValue(settings);
        }
    }

    /**
     * @description  初始化全局配置信息
     * @author CF create on 2018/11/6 15:33
     */
    public static void initDefaultSettingHolderValue() {
        for (SettingsEnum setting:SettingsEnum.values()){
            if(!StringUtils.isEmpty(setting.getDefaultValue() )){
                SettingsHolder.addProperty(setting,setting.getDefaultValue());

            }
        }
    }

    public static void initDefaultSettingHolderValue(Map<String,String>  settings) {
        for (SettingsEnum setting:SettingsEnum.values()){
            if(settings.containsKey(setting.getValue())){

                SettingsHolder.addProperty(setting,settings.get(setting.getValue()));
            }else if(!StringUtils.isEmpty(setting.getDefaultValue() )){
                SettingsHolder.addProperty(setting,setting.getDefaultValue());

            }
        }


    }


    SettingsEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    SettingsEnum(String value, String label, String defaultValue) {
        this.value = value;
        this.label = label;
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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

    /**
     * @description 校验是否有重复key
     * @author CF create on 2018/11/2 13:26
     */
    private static void checkSettingKeysRepeatCondition() {
        Set<String> checkSet=new HashSet<>();
        for (SettingsEnum setting:SettingsEnum.values()){
            if(!checkSet.add(setting.getValue() )){
                System.err.println("全局配置key重复性检查报警 \\n ### 发现重复key:"+setting.getValue()
                        +",\n ### key describe:"+setting.getLabel()+
                        ",\n ### 请检查修正枚举类 SettingsEnum");
                /*DingUtil.sendDingMsg(DingUtil.TEST_TOKEN," 全局配置key重复性检查报警 ",
                        "## 全局配置key重复性检查报警 \n ### 发现重复key:  "+setting.getValue()
                        +",\n ### key describe:"+setting.getLabel()+
                        ",\n ### 请检查修正枚举类 SettingsEnum",true);*/
            }
        }
    }


}
