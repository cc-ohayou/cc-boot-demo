package com.cc.ccbootdemo.core.service;


import com.cc.ccbootdemo.facade.domain.bizobject.param.RedisSearchParam;
import com.cc.ccbootdemo.facade.domain.bizobject.redis.RedisKeyInfo;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.dataobject.RedisKeyDO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/2 11:01.
 */
public interface RedisService {
    /**
     * @description  根据查询条件获取业务关联的key的list
     * @author CF create on 2018/5/2 11:10
     */
    PageInfo<RedisKeyDO> getKeyList(RedisSearchParam param) throws ParamException;


    /**
     * @description  添加key 必须校验存不存在  db和redis都要校验
     * 根据key  parentKey和type 保证键的唯一性
     * 所以这三者是必传参数
     * @author CF create on 2018/5/2 12:46
     */
    void  addKey(RedisKeyDO keyInfo) throws ParamException;

    /** 批量添加key
     * @description
     * @author CF create on 2018/5/2 12:46
     */
    void  addKeys(List<RedisKeyDO> keyInfos);

    /**
     * @description 设置key的超时时间  多少秒后失效
     * key存在设置成功则返回1
     * key不存在则返回 0
     * @author CF create on 2018/5/2 12:47
     */
    void expire(String key, long expireTimeSeconds);
    /**
     * @description
     * 设置key的超时时间  对应秒数的某个时间点过期
     * key存在设置成功则返回1
     * key不存在则返回 0
     * 设置的timeStamp在当前时间之前则等同于删除
     * @author CF create on 2018/5/2 12:47
     */
    void expireAt(String key, long timeStamp);
    /**
     * @description 设置key的超时时间  ·pexpire key milliseconds：键在milliseconds毫秒后过期
     * @author CF create on 2018/5/2 12:47
     */
    void pexpire(String key, long expireTimeSeconds);
    /**
     * @description
     * milliseconds-timestamp键在毫秒级时间戳timestamp后过
     * 设置key的超时时间  对应毫秒数的某个时间点过期
     * key存在设置成功则返回1
     * key不存在则返回 0
     * 设置的timeStamp在当前时间之前则等同于删除
     * @author CF create on 2018/5/2 12:47
     */
    void pexpireAt(String key, long timeStamp);

   /*
      但无论是使用过期时间还是时间戳，
      秒级还是毫秒级，在Redis内部最
            终使用的都是pexpireat
            */

    /**
     * @description
     *  删除key 如果有parentKey则先确定parentKey的类型
     *  然后根据类型删除key
     *  如果是一级key则直接进行删除 即parentKey为空
     * @author CF create on 2018/5/2 14:47
     */
    void delete(String key, String parentKey);

    /**
     * @description 获取key的超时情况
     * 超时的或者不存在的返回-2
     * 没有超时时间的返回为-1
     * 有设置超时且没超时的返回超时时间秒数
     * @author CF create on 2018/5/2 14:54
     */
    void ttl(String key);


    /**
     * @description
     * persist命令可以将键的过期时间清除
     * 成功返回1
     * @author CF create on 2018/5/2 15:08
     */
     void persist(String key);

    /**
     * @description  对于字符串类型键，
     * 执行set命令会去掉过期时间，这个问题很容易
       在开发中被忽视
       所以设置key的时候 如果有超时时间设置，
       随后重新进行超时时间设置
      使用setex命令作为set+expire的组合，不但是原子执行，同时减少了一次
      网络通讯的时间
      zadd key score member [score member ...]
      举例parentKey为  cc:zset 添加member  cc01  score 对应value 一般是排序用的时间分数什么的
      内部到时进行数据类型转换即可 expireType 为空 过期时间为空 不设置过期时间 直接调用
      setKeyValue(String key,String parentKey,String value)
     * @author CF create on 2018/5/2 15:09
     */
     void setKeyValue(String key, String parentKey, String value, String expireType, long expireTime);

     void mSetKeyValue(List<RedisKeyInfo> keyInfos);

    /**
     * @description
     * parentKey不为空
     * 根据类型设置key的值
     * @author CF create on 2018/5/2 15:31
     */
     void setKeyValue(String key, String parentKey, String value);

     /**
     * @description 直接设置key的值
     * @author CF create on 2018/5/2 15:31
     */
     void setKeyValue(String key, String value);

     void mSetKeyValue(String key, String value);


    /**
     * @description
     * @author CF create on 2018/5/2 15:35
     */
     void  reName(String key, String type);

    /**
     * @description dbsize命令会返回当前数据库中键的总数
     * 以dbsize命令的时间复杂度是O（1）
     * @author CF create on 2018/5/2 15:42
     */
     long  dbSize();

    RedisKeyInfo getKeyDetail(RedisSearchParam param) throws ParamException;

    Object getKeyBizTypeList(RedisSearchParam param);
}
