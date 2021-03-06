package com.qunhe.toilet.core.common.settings;

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
    DOWNLOAD_URL_APK("cc-str-apk-download-url","apk下载地址","https://pkg.biligame.com/games/bh3_2.8.0_1_20181211_201637_b8c9b8.apk" ),
    UPDATE_APP_SIGN("update-cc-app-sign","默认为0 不更新  1 更新app","0" ),
    UPYUN_ACCOUNT("upyun-username","又拍云用户名","0" ),
    UPYUN_PWD("upyun-pwd","全部取自redis","" ),
    UPYUN_URL("upyun-url","全部取自redis","" ),
    UPYUN_BUCKET_NAME("upyun-bucket-name","全部取自redis","" ),
    LOGIN_BG_URL("login-bg-url", "登录页面背景图",
            "https://ddy98.b0.upaiyun.com/user/bgImg/1547914325186.jpg" ),
    OPER_BIZ_DETAIL_BG_URL("OPER_BIZ_DETAIL_BG_URL", "操作详情背景图",
            "https://ddy98.b0.upaiyun.com/user/headImages/1548152264892.jpg" ),
    OTHER_CUSTOM_PROPERTIES("OTHER_CUSTOM_PROPERTIES","其他自定义属性",""),
    UPLOAD_PWD("cc-upload-pwd", "","007001manga"),
    DEFAULT_MAIL_USE_DOMAIN("default-mail-use-domain", "验证邮箱可用默认使用域名","www.baidu.com"),
    UPLOAD_FILE_PATH("cc-file-uploadPath","文件上传路径","/opt/web/ccspace/space01/upload/" ),
    UN_INTERCEPT_PATHS("cc-unIntercept-paths","不拦截路径","/v1/upload/uploadFiles,/hello/say" ),
    NO_INTERCEPT_NEED_SIGN("no-intercept-need-sign","登录拦截器开启标识","false" ),

    TOILET_PIT_SLEEP_EARLY_TIME("toilet-pit-sleep-early-time","智慧厕所程序睡眠early时间","8" ),
    TOILET_PIT_SLEEP_LATE_TIME("toilet-pit-sleep-late-time","智慧厕所程序睡眠late时间","23" ),
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
