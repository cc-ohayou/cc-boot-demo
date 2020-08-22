package com.qunhe.toilet.facade.domain.bizobject.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/2 10:56.
 */
@Data
@Accessors(chain=true)
public class RedisSearchParam extends SearchBaseParam {
    private String keyStr;
    private String parentKey;
    /**
    key的类型 hash  String list  set  zset
     */
    private String type;

    private String bizType;

}
