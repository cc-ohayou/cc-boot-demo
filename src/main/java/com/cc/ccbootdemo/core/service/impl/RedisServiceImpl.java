package com.cc.ccbootdemo.core.service.impl;

import com.alibaba.druid.util.StringUtils;

import com.cc.ccbootdemo.core.manager.RedisBizManager;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.mapper.cluster.RedisKeyDOMapper;
import com.cc.ccbootdemo.core.service.RedisService;
import com.cc.ccbootdemo.facade.domain.bizobject.param.RedisSearchParam;
import com.cc.ccbootdemo.facade.domain.bizobject.redis.KeyBizType;
import com.cc.ccbootdemo.facade.domain.bizobject.redis.RedisKeyInfo;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyBizType;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisType;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.common.util.AssertUtil;
import com.cc.ccbootdemo.facade.domain.dataobject.RedisKeyDO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/2 11:10.
 */

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    RedisManager redisManager;
    @Resource
    RedisBizManager redisBizManager;

    @Resource
    RedisKeyDOMapper redisMapper;
    private static Set<String> types;
    Map<String,String> bizTypeMap= RedisKeyBizType.getBizTypesMap();
    private static Logger logger= LoggerFactory.getLogger(RedisServiceImpl.class);
    static{
          types= RedisType.getTypes();
        if(CollectionUtils.isEmpty(types)){
            logger.warn("RedisServiceImpl types init failed");
        }
    }

    @Override
    public PageInfo<RedisKeyDO> getKeyList(RedisSearchParam param) throws ParamException {
        AssertUtil.isNullObj(param.getPageNum(),"页数不能为空");
        Page page = PageHelper.startPage(param.getPageNum(), param.getSize());
        redisMapper.getRedisKeys(param);
        return new PageInfo<>(page);
    }


    @Override
    public void addKey(RedisKeyDO keyInfo) throws ParamException {
        AssertUtil.isNullParamStr(keyInfo.getKey(),"添加的key不能为空");
        AssertUtil.isNullParamStr(keyInfo.getType(),"类型不能为空");
        AssertUtil.isNullParamStr(keyInfo.getName(),"名称不能为空");
        AssertUtil.isNullParamStr(keyInfo.getDescription(),"描述不能为空");
        AssertUtil.isNullParamStr(keyInfo.getDescription(),"描述不能为空");
        Date date=new Date();
        if(StringUtils.isEmpty(keyInfo.getParentKey())){
            keyInfo.setParentKey("");
        }
        keyInfo.setCreateTime(date);
        keyInfo.setUpdateTime(date);
        keyInfo.setParentKey("");
        redisMapper.insertKeyInfo(keyInfo);
    }

    @Override
    public void addKeys(List<RedisKeyDO> keyInfos) {

    }

    @Override
    public void expire(String key, long expireTimeSeconds) {

    }

    @Override
    public void expireAt(String key, long timeStamp) {

    }

    @Override
    public void pexpire(String key, long expireTimeSeconds) {

    }

    @Override
    public void pexpireAt(String key, long timeStamp) {

    }

    @Override
    public void delete(String key, String parentKey) {

    }

    @Override
    public void ttl(String key) {

    }

    @Override
    public void persist(String key) {

    }

    @Override
    public void setKeyValue(String key, String parentKey, String value, String expireType, long expireTime) {

    }

    @Override
    public void mSetKeyValue(List<RedisKeyInfo> keyInfos) {

    }

    @Override
    public void setKeyValue(String key, String parentKey, String value) {

    }

    @Override
    public void setKeyValue(String key, String value) {

    }

    @Override
    public void mSetKeyValue(String key, String value) {

    }

    @Override
    public void reName(String key, String type) {

    }

    @Override
    public long dbSize() {

        return 0;
    }

    @Override
    public RedisKeyInfo getKeyDetail(RedisSearchParam param) throws ParamException {
        AssertUtil.isNullParamObj(param.getKeyStr(),"key不可为空");
        AssertUtil.isNullParamObj(param.getType(),"类型不可为空");
        RedisKeyInfo keyInfo=new RedisKeyInfo();
        AssertUtil.isTrueParam(!types.contains(param.getType()),"非法key类型");
        if(param.getType().equals(RedisType.STRING.getValue())){
            String value=redisManager.get(param.getKeyStr());
            keyInfo.setValue(value);
        }
                return keyInfo;
    }

    @Override
    public Object getKeyBizTypeList(RedisSearchParam param) {
        Map<String,String> bizTypes=redisManager.hgetAll(RedisConstants.KEY_BIZ_TYPE);
        if(CollectionUtils.isEmpty(bizTypes)){
            bizTypes=bizTypeMap ;
        }
        List<KeyBizType> bizTypeList = new ArrayList<>(8);
        Set<Map.Entry<String,String>> set=bizTypes.entrySet();
        for (Map.Entry<String,String> type:set){
            KeyBizType bizType=new KeyBizType();
            bizType.setTypeKey(type.getKey());
            bizType.setTypeValue(type.getValue());
            bizTypeList.add(bizType);
        }
        return bizTypeList;
    }
}
