package com.cc.ccbootdemo.facade.domain.common.util.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/27 17:14.
 */
public class JedisSentinelUtil {
//    masterName——主节点名
//    ·sentinels——Sentinel节点集合
//    ·poolConfig——common-pool连接池配置
//    ·connectTimeout——连接超时
//    soTimeout——读写超时
//    password——主节点密码
//    database——当前数据库索引
//    clientName——客户端名

    //通过简单的几个参数获取JedisSentinelPool

    private static Logger logger= LoggerFactory.getLogger(JedisSentinelUtil.class);

    private static String masterName="mymaster";
    private static String password="redis-dev-4";
    private static Set<String> sentinelSet;
    private static GenericObjectPoolConfig poolConfig;
    private static int timeout=5000;


    private final static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。

    public final static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。
    // 如果超过等待时间，则直接抛出JedisConnectionException；

    public final static int MAX_WAIT = 10000;
    private final static int TIMEOUT = 10000;

    //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)

    public final static int MIN_IDLE_TIME = 60 * 1000 * 5;
    //5分钟释放
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，
    // 则得到的jedis实例均是可用的；

    public final static boolean TEST_ON_BORROW = true;

    static {
        sentinelSet=new HashSet<>();
        sentinelSet.add("118.31.73.19:26380");
        sentinelSet.add("118.31.73.19:26381");
        sentinelSet.add("118.31.73.19:26379");
        poolConfig=new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(MAX_ACTIVE);
        ///表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出超时异常
        poolConfig.setMaxWaitMillis(MAX_WAIT);
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setTestOnBorrow(TEST_ON_BORROW);
        poolConfig.setMinEvictableIdleTimeMillis(MIN_IDLE_TIME);
    }



    private static JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName,
            sentinelSet,poolConfig,password);


    private static Jedis getJedis(){
        Jedis jedis=null;
        try {
            jedis = jedisSentinelPool.getResource();
            } catch (Exception e){
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return jedis;

    }

    public static void main(String[] args) {

        Jedis  jedis=getJedis();
        Set<String>  set=jedis.smembers("cc-set");
        System.out.println(set.toString());

    }
}
