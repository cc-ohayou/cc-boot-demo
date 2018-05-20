package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.RedisBizManager;
import com.cc.ccbootdemo.core.mapper.cluster.RedisKeyDOMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/10 15:48.
 */
@Component
public class RedisBizManagerImpl implements RedisBizManager {

    @Resource
    RedisKeyDOMapper redisKeyDOMapper;

    @Override
    public Map<String, Object> getkeyBizTypeList() {
        return redisKeyDOMapper.getAllKeyBizTypes();
    }
}
