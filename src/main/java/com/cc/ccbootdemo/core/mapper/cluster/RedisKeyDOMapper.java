package com.cc.ccbootdemo.core.mapper.cluster;


import com.cc.ccbootdemo.facade.domain.bizobject.param.RedisSearchParam;
import com.cc.ccbootdemo.facade.domain.dataobject.RedisKeyDO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RedisKeyDOMapper extends Mapper<RedisKeyDO> {
    /**
     * @description
     * @author CF create on 2018/5/2 19:32
     */
    List<RedisKeyDO> getRedisKeys(RedisSearchParam param);

    int insertKeyInfo(RedisKeyDO keyInfo);

    Map<String,Object> getAllKeyBizTypes();
}