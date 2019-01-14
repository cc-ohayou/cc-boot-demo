package com.cc.ccbootdemo.core.manager;


import com.cc.ccbootdemo.facade.domain.ThreadLocals.BaseLockSign;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 避免将底层的redis ip 等全局配置暴露给外界 对redis连接的获取和释放自动完成,避免忘记释放的问题
 * 给外界提供 统一的、抽象的  kv查询服务、排序服务器、队列服务、set服务等
 * <p>
 * Create By GarryKing on 2017/9/17 上午10:03
 */
public interface RedisManager {

    /**
     * @description
     * @author CF create on 2018/3/15 9:36
     */
    Map<String, String> hgetAll(String key);

    /**
     * @description
     * @author CF create on 2018/3/15 9:35
     */
    String hget(String key, String field);

    /**
     * @description 获取散列表中的多个属性的值
     * @author CF create on 2018/3/15 9:35
     */
    List<String> hmget(String key, String... fields);

    /**
     * 设置hash类型的key 已存在则会覆盖
     *
     * @param key 键
     * @param field 属性
     * @param value 值
     * @return 第一次设置成功返回1 key已经存在则返回0
     */
    long hset(String key, String field, String value);


     /**
     * @description 第一次设置hash类型的key某个field时可以设置超时时间  后面再设置field调用非超时的即可
     * @author CF create on 2018/3/15 9:39
     */
    long hset(String key, String field, String value, int expireTime);


     /**
     * @description 批量设置hash key的多个field的值
     * @author CF create on 2018/3/15 9:40
     */
    void hmset(String key, Map<String, String> fieldValueMap);


     /**
     * @description 设置简单的String类型的key 已存在则会覆盖
     * @author CF create on 2018/3/15 9:40
     */
    void set(String key, String value);

    /**
     * @description 获取key的值
     * @author CF create on 2017/9/20 15:07
     */
    String get(String key);

    /**
     * @description 设置key值同时设置过期时间 单位s
     * @author CF create on 2017/9/20 15:27
     */
    void set(String key, String value, int expireTime);

    /**
     * @description 根据需求自行对任何key设置过期时间
     * @author CF create on 2017/9/20 15:29
     */
    long expire(String key, int expireTime);

    /**
     * @description 删除key
     * @author CF create on 2017/9/20 15:30
     */
    long del(String key);

    /**
     * @description  删除hash散列表中的某个键值
     * @author CF create on 2018/3/15 9:30
     */
    long hdel(String key, String field);

    /**
     * @description 存在设置值并返回1 不存在返回
     * @author CF create on 2017/10/25 9:55
     */
    long hsetnx(String key, String field, String value);

    /**
     * @description 对一个key进行自增操作 默认自增1 返回增加后的数值
     * @author CF create on 2018/3/15 9:29
     */
    void incr(String tradeKey);

    /**
     * @description 往有序集合（sorted set）中添加键值对同时附带一个分数用于以后排序使用
     * @author CF create on 2018/3/15 9:28
     */
    void zadd(String key, long score, String value);

    /**
     * @description 获取对应的配置
     * * $ redis-cli config get '*'
     * 1. "dbfilename"
     * 2. "dump.rdb"
     * 3. "requirepass"
     * 4. (nil)
     * 5. "masterauth"
     * 6. (nil)
     * 7. "maxmemory"
     * 8. "0\n"
     * 9. "appendfsync"
     * 10. "everysec"
     * 11. "save"
     * 12. "3600 1 300 100 60 10000"
     *
     *  * $ redis-cli config get 'm*'
     * 1. "masterauth"
     * 2. (nil)
     * 3. "maxmemory"
     * 4. "0\n"
     * @author CF create on 2018/3/15 9:55
     */
     List<String> configGet(String pattern);
    /**
     * @description 设置某个参数的值 常用设置
     * loglevel requirepass maxmemory appendfsync
     *
     *  For instance the command CONFIG
     *  SET save "3600 10 60 10000"
     *  will configure the server to issue a background saving of the RDB file every
     *  3600 seconds if there are at least 10 changes in the dataset, and every 60 seconds if there are
     *  at least 10000 changes. To completely disable automatic snapshots just set the parameter as an
     *  empty string.
     * @author CF create on 2018/3/15 10:11
     */
    String configSet(final String parameter, final String value) ;
    /**
     * @description 在某个频道发布消息
     * @author CF create on 2018/3/15 10:08
     */
     Long publish(String channel, String message);

    /**
     * @description 使用具体的jedisPubSub订阅某个频道的消息 或者多个频道的 具体频道
     * @author CF create on 2018/3/15 10:08
     */
     void subscribe(JedisPubSub jedisPubSub, String describe, String... channels);

    /**
     * @description 订阅某种规则的频道消息
     * @author CF create on 2018/3/15 10:10
     */
     void psubscribe(JedisPubSub jedisPubSub, String describe, String... patterns);


    boolean reachRateLimit(String key, String s, String s1);

    /**
     * @description
     * 借助redis完成全局的分布式锁
     * 业务执行完后注意unlock 否则会持有该锁60s 默认值
     * 其他线程才有机会再次获取
     * 限制：单机模式
     * 集群下使用redession来实现 超高并发 集群不同步的情况下锁不住
     * 也可借助zookeeper来实现分布式锁
     *
     * @author CF create on 2018/3/21 9:40
     */
    boolean  lock(String key, String lockModel, BaseLockSign lockThreadLocal) throws Exception;

    /**
     * @description 使用完释放锁
     * @author CF create on 2018/3/21 9:41
     */
    void  unLock(String key, BaseLockSign lockThreadLocal);

    /**
     * @description 获取集合
     * @author CF create on 2018/3/23 11:21
     */
    Set<String> smembers(String key);

    /**
     * @description 根据ip ua信息限流特定url
     * @author CF create on 2018/3/26 15:51
     */
    boolean ipUaInfoReachLimit(String uaInfo, String realIp, String url);

    /**
     * @description  查看key是否存在
     * @author CF create on 2018/7/9 12:21
     */
    boolean exists(String key,Jedis jedis);

    /**
     * @description 遍历获取hash key中的所有元素
     * @author CF create on 2019/1/11 10:17
     */
     List hscan(String key, ScanParams params, Jedis jedis) ;

    /**
     * @description 遍历list元素
     * @author CF create on 2019/1/11 10:20
     */
     List<String>  lrange(String key, int start,int end, Jedis jedis) ;

    /**
     * @description 获取列表总数
     * @author CF create on 2019/1/11 10:42
     */
    long  llen(String key, Jedis jedis) ;

    }
