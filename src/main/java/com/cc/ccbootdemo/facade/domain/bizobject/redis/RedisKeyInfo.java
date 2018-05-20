package com.cc.ccbootdemo.facade.domain.bizobject.redis;

import com.cc.ccbootdemo.facade.domain.dataobject.RedisKeyDO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/2 11:03.
 */
@Data
@Accessors(chain=true)
public class RedisKeyInfo {

    private RedisKeyDO baseInfo;
    private String expireTime;
    private String expireType;
    private String value;
    private String exists;
    //key的长度  hash  set  list 和zset
    private long length;
    private Map    mapValue;
    private List   listValue;


    /*private class BaseKeyInfo{

        // 根据key  parentKey和type 保证键的唯一性
        private String key;
        private String name;
        //父级key的值  譬如hash类型的key    parentKey 为 HASH_TEST  属性（key此时对应为属性）name
        // type为hash   获取key的value命令即为 hget HASH_TEST name
        // type为set  list   string zset 时各不相同
        private String parentKey;

        private String type;
        private String description;
        private String createTime;
        private String updateTime;
        //key关联的业务类型  譬如说是 结算相关的  存储 ps-settle
        //开关类的 存储switch

        private String keyBizType;
    }*/

}
