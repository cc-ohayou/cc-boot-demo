package com.cc.ccbootdemo.facade.domain.common.constants;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/6/22 16:00.
 */
public class RedisConstants {







    /**
     * 锁等待时间，防止线程饥饿
     */
    public static final int timeoutMsecs = 1* 1000;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    public static final int expireMsecs = 5 * 1000;

    /**
     * 拦截校验相关配置redis key和常量
     */
    public static final String RATE_LIMIT_SCRIPT = "if redis.call('exists',KEYS[1])==1 and tonumber(redis.call('get',KEYS[1]))>tonumber(ARGV[2]) then return 0 end local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1])   end if times>tonumber(ARGV[2]) then return 0 end return 1";


    public static final String IP = "PS-STR-IP-";
    public static final String IP_MINUTE = "-MINUTE";
    public static final String IP_HOUR = "-HOUR";
    public static final String IP_DAY = "-DAY";
    public static final String IP_MONTH = "-MONTH";
    //UA信息
    public static final String UA = "PS-STR-UA-";
    public static final String UA_MINUTE = "-MINUTE";
    public static final String UA_HOUR = "-HOUR";
    public static final String UA_DAY = "-DAY";
    public static final String UA_MONTH = "-MONTH";


    public static final String ONE_MINUTE ="60" ;
    public static final String ONE_MINUTE_LIMIT_COU ="5" ;
    public static final String ONE_HOUR ="3600" ;
    public static final String ONE_HOUR_LIMIT_COU = "50";
    public static final String ONE_DAY = "86400";
    public static final String ONE_DAY_LIMIT_COU ="100" ;
    public static final String ONE_MONTH = "2678400";
    public static final String ONE_MONTH_LIMIT_COU ="500" ;
    //频率限制校验的开关
    public static final String IP_SWITCH = "ps-str-ip-ua-limit-switch";

    public static final String COU_LIMIT_INFO = "PS-HASH-IP-COU-LIMIT-CONFIG";
    public static final String COU_LIMIT_INFO_MIN = "min-cou";
    public static final String COU_LIMIT_INFO_HOUR = "hour-cou";
    public static final String COU_LIMIT_INFO_DAY = "day-cou";
    public static final String COU_LIMIT_INFO_MONTH = "month-cou";

    public static final String RATE_LIMIT_WHITE_IP = "ps-set-whiteips";
    public static final String RATE_LIMIT_BLACK_IP = "ps-set-blackips";
    public static final String IP_UA_LIMIT_URLS = "ps-str-ip-ua-limit-urls";

    public static final String KEY_BIZ_TYPE = "TOOL_HASH_KEY_BIZ_TYPE";
}
