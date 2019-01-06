package com.cc.ccbootdemo.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.facade.domain.ThreadLocals.BaseLockSign;
import com.cc.ccbootdemo.facade.domain.bizobject.Manga;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyEnum;
import com.cc.ccbootdemo.facade.domain.common.util.DateUtil;
import com.cc.ccbootdemo.facade.domain.common.util.log.RateLimitCheckLog;
import com.cc.ccbootdemo.facade.domain.common.util.log.RedisLog;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/18 18:49.
 */
@Component
public class RedisManagerImpl extends RedisConstants implements RedisManager {
    /**
     * redis 全局配置
     */
    //频率限制 lua脚本

    public static final String RATE_LIMIT_SCRIPT = "if redis.call('exists',KEYS[1])==1 and tonumber(redis.call('get',KEYS[1]))>tonumber(ARGV[2]) then return 0 end local times = redis.call('incr',KEYS[1]) if times == 1 then redis.call('expire',KEYS[1],ARGV[1])   end if times>tonumber(ARGV[2]) then return 0 end return 1";

    //redis lock用常量
    //锁允许重试模式
    public static final String LOCK_MODEL_ALLOW_RETRY = "allowRetry";
    //分布式锁开关 默认为空不打开
    public static final String REDIS_LOCK_SWITCH = "ps-str-redis-lock-switch";

    /**
     * redis lock模式  不为空则说明使用降级模式
     * 一旦lock被某一线程持有 其他线程直接返回false 不在进行重试操作 降低线程sleep重试的性能损耗
     */
    public static final String LOCK_MODEL = "ps_str_lock_model";


    private static final int REDIS_RETRY_COUNT = 10;
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，
    // 则此时pool的状态为exhausted(耗尽)。

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

    private static JedisPool jedisPool = null;

    private static JedisSentinelPool jedisSentinelPool = null;

    private static RedisLog logger = new RedisLog();
    private RateLimitCheckLog limitLog = new RateLimitCheckLog();

    private static String rateLimitShaStr;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 1 * 1000;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    private static Map<Integer, Long> initMap = new ConcurrentHashMap<>();

    //   初始化Redis连接池
    static {
//        initJedisPool();
        initSentinelPool();
        initSpecialScript();

    }

    private static void initSpecialScript() {
        Jedis jedis = getJedis();
        try {
            rateLimitShaStr = jedis.scriptLoad(RATE_LIMIT_SCRIPT);
        } catch (Exception e) {
            logger.error("redis init error rateLimitShaStr init failed", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取Jedis实例
     */
    public static Jedis getJedis() {
        /*Jedis jedis = null;
        int count = 0;
        do {
            try {
                if (jedisPool == null) {
                    initJedisPool();
                }
                jedis = jedisPool.getResource();
                logger.info("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例情况," +
                        "active=" + jedisPool.getNumActive() + ",idle=" + jedisPool.getNumIdle() + ",waiters=" + jedisPool.getNumWaiters());
            } catch (Exception e) {
                logger.error("Redis Exception :", e);
                logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例情况," +
                        "active=" + jedisPool.getNumActive() + ",idle=" + jedisPool.getNumIdle() + ",waiters=" + jedisPool.getNumWaiters());
                if (jedisPool.getNumIdle() == 0 && jedisPool.getNumActive() == MAX_ACTIVE) {
                    logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例耗尽," +
                            "active=" + jedisPool.getNumActive() + jedisPool.getNumIdle());
                }
                jedisPool = null;
                // 防止意外情况资源耗尽 重新初始化jedisPool 这个只是一解燃眉之急而已 最终解决还是要在程序中注意及时释放连接实例
                initJedisPool();
            }
            count++;
        } while (jedis == null && count < REDIS_RETRY_COUNT);
        //重试10次
        return jedis;*/
        return getJedisBySentinelPool();
    }

    /**
     * 获取Jedis实例 哨兵模式
     */
    public static Jedis getJedisBySentinelPool() {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                if (jedisSentinelPool == null) {
                    initSentinelPool();
                }
                jedis = jedisSentinelPool.getResource();
                logger.info("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisSentinelPool中实例情况," +
                        "active=" + jedisSentinelPool.getNumActive() + ",idle=" + jedisSentinelPool.getNumIdle()
                        + ",waiters=" + jedisSentinelPool.getNumWaiters());
            } catch (Exception e) {
                logger.error("Redis Exception :", e);
                logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisSentinelPool中实例情况," +
                        "active=" + jedisSentinelPool.getNumActive() + ",idle=" + jedisSentinelPool.getNumIdle()
                        + ",waiters=" + jedisPool.getNumWaiters());
                if (jedisSentinelPool.getNumIdle() == 0 && jedisSentinelPool.getNumActive() == MAX_ACTIVE) {
                    logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisSentinelPool中实例耗尽," +
                            "active=" + jedisSentinelPool.getNumActive() + jedisSentinelPool.getNumIdle());
                }
                jedisSentinelPool = null;
                // 防止意外情况资源耗尽 重新初始化jedisSentinelPool 这个只是一解燃眉之急而已
                // 最终解决还是要在程序中注意及时释放连接实例
                initSentinelPool();
            }
            count++;
        } while (jedis == null && count < REDIS_RETRY_COUNT);
        //重试10次
        return jedis;
    }

    private static void initSentinelPool() {
        FileInputStream in = null;//System.getenv("CC_RESOURCE");
        try {
            in = new FileInputStream(new File(System.getenv("CC_RESOURCE_DIR") + "/cc_jedis.properties"));
            Properties prop = new Properties();
            prop.load(in);

            logger.info("initSentinelPool properties="+prop);
            String sentinel01 = prop.getProperty("jedis.sentinel01").trim();
            String sentinel02 = prop.getProperty("jedis.sentinel02").trim();
            String sentinel03 = prop.getProperty("jedis.sentinel03").trim();
            String pass = prop.getProperty("jedis.pass").trim();
            String clusterName = prop.getProperty("jedis.clusterName").trim();
            Set<String> sentinels = new HashSet<>();
            sentinels.add(sentinel01);
            sentinels.add(sentinel02);
            sentinels.add(sentinel03);

            try {
                jedisSentinelPool = new JedisSentinelPool(clusterName, sentinels, pass);

            } catch (Exception e) {
                logger.error("####Redis sentinel getResource Exception :", e);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("Redis 连接配置文件 jedis.properties 加载失败！");
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }


    }





    //初始化Redis连接池 适用于单点
    private static synchronized void initJedisPool() {
        Long lastInitTime=initMap.get(101);
        //10s内只允许初始化一次
        if(lastInitTime!=null&&System.currentTimeMillis()-lastInitTime<=10000){
            return;
        }else{
            initMap.put(101,System.currentTimeMillis());
        }
        FileInputStream in = null;//System.getenv("CC_RESOURCE");
        try {
            in = new FileInputStream(new File(System.getenv("CC_RESOURCE_DIR") + "/cc_jedis.properties"));
            Properties prop = new Properties();
            prop.load(in);
            String ADDR = prop.getProperty("jedis.host").trim();
            int PORT = Integer.parseInt(prop.getProperty("jedis.port").trim());
            String AUTH = prop.getProperty("jedis.auth").trim();
            JedisPoolConfig config = initConfig();
            logger.info("ADDR:" + ADDR + ",PORT:" + PORT + ",AUTH:" + AUTH);
            if (StringUtils.isEmpty(AUTH)) {
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            } else {
                jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            }
            logger.info("Redis连接池初始化成功，Host" + ADDR + ",PORT:" + PORT);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("Redis 连接配置文件 jedis.properties 加载失败！");
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }

    private static JedisPoolConfig initConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);///表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出 　
        config.setMinEvictableIdleTimeMillis(MIN_IDLE_TIME);
        config.setTestOnBorrow(TEST_ON_BORROW);
        return config;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = getJedis();
        Map<String, String> map = new HashMap<>();
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return map;
    }
     private Map<String, String> hgetAll(String key, Jedis jedis) {
        return  jedis.hgetAll(key);
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = getJedis();
        String str = "";
        try {
            str = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return str;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = getJedis();
        List<String> list = new ArrayList<>();
        try {
            list = jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return list;
    }

    @Override
    public long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("redis hset failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hset(String key, String field, String value, int expireTime) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hset(key, field, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis hset failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public void hmset(String key, Map<String, String> fieldValueMap) {
        Jedis jedis = getJedis();

        try {
            jedis.hmset(key, fieldValueMap);
        } catch (Exception e) {
            logger.error("redis hmset failed, key=" + key + ",fieldValueMap=" + fieldValueMap.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = getJedis();

        try {
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("redis set failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        String value = "";
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("redis get failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    @Override
    public void set(String key, String value, int expireTime) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis set expire failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public long expire(String key, int expireTime) {
        Jedis jedis = getJedis();
        long time = 0l;
        try {
            time = jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis del failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return time;
    }

    @Override
    public long del(String key) {
        Jedis jedis = getJedis();
        long count = 0l;
        try {
            count = jedis.del(key);
        } catch (Exception e) {
            logger.error("redis del failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hdel(String key, String field) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error("redis hdel failed, key=" + key + ",field=" + field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hsetnx(String key, String field, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error("redis hsetnx failed, key=" + key + ",field=" + field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public void incr(String tradeKey) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.incr(tradeKey);
        } catch (Exception e) {
            logger.error("redis incr failed, key=" + tradeKey);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void zadd(String key, long score, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("redis zadd failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public List<String> configGet(String pattern) {
        Jedis jedis = getJedis();
        List<String> result = null;
        try {
            result = jedis.configGet(pattern);
        } catch (Exception e) {
            logger.error("redis configGet failed, pattern=" + pattern);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public String configSet(String parameter, String value) {

        Jedis jedis = getJedis();
        String result = null;
        try {
            result = jedis.configSet(parameter, value);
        } catch (Exception e) {
            logger.error("redis configSet failed, parameter=" + parameter + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Long publish(String channel, String message) {

        Jedis jedis = getJedis();
        Long result = null;
        try {
            result = jedis.publish(channel, message);
        } catch (Exception e) {
            logger.error("redis publish failed, channel=" + channel + ",message=" + message);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String describe, String... channels) {
        Jedis jedis = getJedis();
        try {
            jedis.subscribe(jedisPubSub, channels);
        } catch (Exception e) {
            logger.error("redis subscribe failed, jedisPubSubName=" + describe + ",channels=" + Arrays.toString(channels));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String describe, String... patterns) {
        Jedis jedis = getJedis();
        try {
            jedis.subscribe(jedisPubSub, patterns);
        } catch (Exception e) {
            logger.error("redis psubscribe failed, jedisPubSubName=" + describe + ",patterns=" + Arrays.toString(patterns));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    @Override
    public  Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> members = new HashSet<>();
        try {
            members = jedis.smembers(key);
        } catch (Exception e) {
            logger.error("redis smembers failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return members;
    }

    public  Set<String> smembers(String key,Jedis jedis) {
        return jedis.smembers(key);
    }
    @Override
    public boolean lock(String lockKey, String lockModel, BaseLockSign lockThreadLocal) {

        //重复使用锁的情况 只加一次
        if (lockThreadLocal.getLockSign()) {
            return true;
        }
        Jedis jedis = getJedis();
        //jedis连接获取不到的情况直接返回true 也即redis lock不再起效 所有进来的都直接认为成功
        if (jedis == null || !StringUtils.isEmpty(jedis.get(REDIS_LOCK_SWITCH))) {
            return true;
        }
        boolean lockSign = false;
        try {
            boolean lockModelHighCostAllow = StringUtils.isEmpty(jedis.get(LOCK_MODEL));
            //全局允许且采用高性能重试模式
            if (lockModelHighCostAllow && LOCK_MODEL_ALLOW_RETRY.equals(lockModel)) {
                /*重试模式 场景举例  甲客户充值时遇到task占用余额lock 此时可以等待一秒钟左右进行重试
                  task操作完后可以继续完成充值 而不是直接返回系统繁忙的提示 这样体验不是很好 系统容错差
                  延迟一定的毫秒数,最多延迟一秒钟  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                  只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进程,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                  使用随机的等待时间可以一定程度上保证公平性
                 */
                lockSign = retryGetLock(jedis, lockKey);
            } else {
                //全局不允许或者不使用重试模式都走此种锁模式
                //不提供重试 适用于秒杀抢夺一件产品的场景 直接返回秒杀失败即可
                lockSign = tryGetLock(jedis, lockKey);
            }
        } catch (Exception e) {
            logger.error("redis lock  failed, lockKey=" + lockKey, e);
        } finally {
            jedis.close();
        }
        //只有获得lock的本线程才设置lockSign为true

        if (lockSign) {
            //本地线程变量需要谨慎使用，大批量使用时尤其注意要 用完调用remove方法释放 否则会引起内存泄漏
            //参考博客 https://www.jianshu.com/p/33c5579ef44f
            setThreadLocalLockSign(lockThreadLocal, true);
        }
        return lockSign;
    }

    private void setThreadLocalLockSign(BaseLockSign lockThreadLocal, boolean lockSign) {
        lockThreadLocal.setLockSign(lockSign);
    }

    private boolean retryGetLock(Jedis jedis, String lockKey) throws InterruptedException {
        int timeout = timeoutMsecs;
        int waitMsecs = new Random().nextInt(100) + 100;//100~200ms
        int retryCou = 0;
        while (timeout >= 0) {
            if (tryGetLock(jedis, lockKey)) {
                return true;
            }
            timeout -= waitMsecs;
            Thread.sleep(waitMsecs);
            retryCou++;
        }
        if (retryCou > 0) {
            logger.info("retryGetLock failed,thread=" + Thread.currentThread().getName() + ",retryCou=" + retryCou + ",lockKey=" + lockKey);
        }
        return false;
    }

    private boolean tryGetLock(Jedis jedis, String lockKey) {
        long expires = System.currentTimeMillis() + expireMsecs;
        String expiresStr = String.valueOf(expires); //锁到期时间
        if (jedis.setnx(lockKey, expiresStr) == 1) {//首次设置该值，直接返回成功 获得锁 返回
            // lock acquired
            return true;
        }
        // 不是第一次设置该锁 ，也就是别人抢先了，但万一那人有什么情况
        // （倒霉的redis挂了且key的信息还没同步好 亿万分之一的几率 或者超时 太磨叽）总之锁又被释放了
        //  那别人就又有竞争机会了 继续进行下面的尝试
        String currentValueStr = this.get(lockKey); //redis里的时间
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            // lock is expired
            String newExpireTime = String.valueOf(System.currentTimeMillis() + expireMsecs);
            //竞态获取资格
            String oldValueStr = jedis.getSet(lockKey, newExpireTime);
            //获取上次的到期时间，并设置现在的锁到期时间，
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unLock(String key, BaseLockSign lockThreadLocal) {
        //针对余额操作的分布式锁
        if (threadLocalLockSignTrue(lockThreadLocal)) {
          /*  try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            logger.info(Thread.currentThread().getName() + ",lockLocal=" + lockThreadLocal.toString() + " redis unLock acquired,unlockKey=" + key);
            //使用ThreadLocal就跟加锁完要解锁一样，用完就清理  以免引起内存泄漏
            del(key);
        }
        //必须删除本地线程变量否则会引起内存泄漏
        //参考博客 https://www.jianshu.com/p/33c5579ef44f
        lockThreadLocal.clear();
    }

    private boolean threadLocalLockSignTrue(BaseLockSign lockThreadLocal) {
        return lockThreadLocal.getLockSign();
    }
    @Override
    public boolean reachRateLimit(String key, String timeSeconds, String limitTimes) {

        Jedis jedis = getJedis();
        long result = 1;
        try {
            String shaStr = jedis.get("ipRateLimitLuaSha");
            shaStr = StringUtils.isEmpty(shaStr) ?
                    rateLimitShaStr : shaStr;
            Object o = jedis.evalsha(shaStr, 1, key, timeSeconds, limitTimes);
            result = (long) o;
        } catch (Exception e) {
            logger.error("redis reachRateLimit script failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result == 0;
    }

    public boolean reachRateLimit(String key, String timeSeconds, String limitTimes,Jedis jedis) {
        String shaStr = jedis.get("ipRateLimitLuaSha");
        shaStr = StringUtils.isEmpty(shaStr) ?
                rateLimitShaStr : shaStr;
        Object o = jedis.evalsha(shaStr, 1, key, timeSeconds, limitTimes);
        long result = (long) o;
        return result == 0;
    }
    @Override
    public boolean ipUaInfoReachLimit(String ua, String ip, String url) {
        Jedis jedis = getJedis();
        try {
            if (simpleIpUaCheckLimit(ip, ua, jedis) || specialUrlCheckLimit(url, ua, ip, jedis)) {
                return true;
            }
        } catch (Exception e) {
            limitLog.warn("ipUaInfoReachLimit failed", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }



    @Override
    public boolean exists(String keyStr, Jedis jedis) {
        boolean closeFlag=false;
        if(jedis==null){
            jedis=getJedis();
            closeFlag=true;
        }
        boolean exists=false;
        try {
            exists = jedis.exists(keyStr);
        } catch (Exception e) {
            logger.warn("redis exists failed, key =" + keyStr);
        } finally {
            if (jedis != null&&closeFlag) {
                jedis.close();
            }
        }
        return exists;
    }
    /**
     * @description 简单校验ip ua信息是否为空和ip是否在白名单
     * @author CF create on 2018/3/26 15:58
     */
    private boolean simpleIpUaCheckLimit(String realIp, String uaInfo, Jedis jedis) {
        if (StringUtils.isEmpty(realIp) || realIp.length() <= 0 || StringUtils.isEmpty(uaInfo)) {
            limitLog.warn("!!!###realIp or uaInfo is illegal empty,limit on ");
            return true;
        }
        if (smembers(RATE_LIMIT_BLACK_IP, jedis).contains(realIp)) {
            limitLog.warn("black ip=" + realIp + ",limit on");
            return true;
        }
        if (smembers(RATE_LIMIT_WHITE_IP, jedis).contains(realIp)) {
            limitLog.warn("white ip=" + realIp + ",limit off");
            return false;
        }
        return false;
    }

    /**
     * @description ip url频率校验 四个级别
     * @author CF create on 2018/1/8 10:27
     */
    private boolean specialUrlCheckLimit(String url, String uaInfo, String realIp, Jedis jedis) {
        limitLog.info("rateLimit url=" + url);
        Map<String, String> limitCouInfo = hgetAll(COU_LIMIT_INFO, jedis);
        //一分钟限制
        String oneMinuteLimit = getRealLimitCou(limitCouInfo.get(COU_LIMIT_INFO_MIN), ONE_MINUTE_LIMIT_COU);
        if (reachLimit(url, UA_MINUTE, uaInfo, realIp, IP_MINUTE, ONE_MINUTE, oneMinuteLimit, jedis)) {
            return true;
        }
        //一小时限制
        String hourLimit = getRealLimitCou(limitCouInfo.get(COU_LIMIT_INFO_HOUR), ONE_HOUR_LIMIT_COU);
        if (reachLimit(url, UA_HOUR, uaInfo, realIp, IP_HOUR, ONE_HOUR, hourLimit, jedis)) {
            return true;
        }
        //一天的访问限制
        String dayLimitCou = getRealLimitCou(limitCouInfo.get(COU_LIMIT_INFO_DAY), ONE_DAY_LIMIT_COU);
        if (reachLimit(url, UA_DAY, uaInfo, realIp, IP_DAY, ONE_DAY, dayLimitCou, jedis)) {
            return true;
        }
        //一个月的访问限制
        String monthLimitCou = getRealLimitCou(limitCouInfo.get(COU_LIMIT_INFO_MONTH), ONE_MONTH_LIMIT_COU);
        return reachLimit(url, UA_MONTH, uaInfo, realIp, IP_MONTH, ONE_MONTH, monthLimitCou, jedis);
    }

    /**
     * @description 获取对应key的频率限制
     * @author CF create on 2018/1/8 10:27
     */
    private String getRealLimitCou(String value, String defaultCou) {
        return StringUtils.isEmpty(value) ? defaultCou : value;
    }

    private String getUAKey(String url, String baseKey, String str2) {
        return UA + url + "-" + baseKey + str2;
    }

    private String getIPKey(String url, String baseKey, String str2) {
        return IP + url + "-" + baseKey + str2;
    }

    //UA信息只和系统版本浏览器型号 a手机型号相关很容易一样所以同时校验两个信息都满足才阻止访问
    private boolean reachLimit(String url, String uaLimitType, String uaInfo,
                               String realIp, String ipLimitType,
                               String time, String limitCou, Jedis jedis) {
        boolean ipFlag = reachIpLimit(url, realIp, ipLimitType, time, limitCou, jedis);
        boolean uaFlag = reachUALimit(url, uaLimitType, uaInfo, time, limitCou, jedis);
        return ipFlag && uaFlag;
    }

    private boolean reachUALimit(String url, String uaType, String uaInfo,
                                 String time, String limitCou, Jedis jedis) {
        //同一设备短时间内访问次数过多进行限制
        if (reachRateLimit(getUAKey(url, String.valueOf(hash(uaInfo)), uaType), time, limitCou, jedis)) {
            limitLog.warn("!!!#####url=" + url + ", uaInfo:" + uaInfo + ",时间=" + time + "s内访问次数超限,limitTimes=" + limitCou);
            limitLog.warn("!!!#####uaInfo hash=" + String.valueOf(hash(uaInfo)));
            return true;
        }
        return false;
    }

    private boolean reachIpLimit(String url, String realIp, String ipType,
                                 String time, String limitCou, Jedis jedis) {
        //访问次数到达上限 暂时封锁ip访问次数
        if (reachRateLimit(getIPKey(url, realIp, ipType), time, limitCou, jedis)) {
            limitLog.warn("!!!#####url=" + url + ", ip:" + realIp + ",时间=" + time + "s内访问次数超限,limitTimes=" + limitCou);
            return true;
        }
        return false;
    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    static class CCTestPubSub extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            System.out.println(message);
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {

            System.out.println(channel);

        }

    }

    private static ExecutorService ratePool = initPool("ratePool");// newWorkStealingPool(16);  适用于任务之间相差时间较大 波动性很大的场景

    private static ExecutorService initPool(String type) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(type + "-SettleTaskJob-pool-%d").build();
        return new ThreadPoolExecutor(8, 8,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10000),
                namedThreadFactory);
//                new MyThreadTaskAbortPolicy(logger, type));
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
//        incrTest(jedis);
//        hsetTest(jedis);
//        transTest(jedis);
//        pipeLineTest(jedis);
//        System.out.println(jedis.get("cc"));
//        publishTest(jedis);
//        subscribeTest(jedis);
//
//        Jedis sentinelJedis = getJedisBySentinelPool();
//        System.out.println(sentinelJedis.get("cc"));

        String str="[{\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 137743,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"21\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/ac7a5aa48472786d9f16a248590a610f3319ef7a.jpg\",\n" +
                "    \"danmaku_count\": 161249,\n" +
                "    \"ep_id\": 259665,\n" +
                "    \"favorites\": 137743,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545972000,\n" +
                "    \"lastupdate_at\": \"2018-12-28 12:40:00\",\n" +
                "    \"isNew\": true,\n" +
                "    \"play_count\": 6929133,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 23695,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/0c85a95abe82e3af8b4659a6cf4584c0ea27cfec.jpg\",\n" +
                "    \"title\": \"峡谷重案组\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 5074458,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"南国总集篇（五）\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/cbb20ee03e97a9f3ad2e1506a10fd1271f1c584a.jpg\",\n" +
                "    \"danmaku_count\": 8330441,\n" +
                "    \"ep_id\": 259577,\n" +
                "    \"favorites\": 5074458,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545966000,\n" +
                "    \"lastupdate_at\": \"2018-12-28 11:00:00\",\n" +
                "    \"isNew\": true,\n" +
                "    \"play_count\": 419107034,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 2543,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/d97d43b12e7c698369bbe8af3c029b24216a6a66.jpg\",\n" +
                "    \"title\": \"狐妖小红娘\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 227002,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"5\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/1fba6095a44aa1fa6e19adb7b50e58a288be623c.jpg\",\n" +
                "    \"danmaku_count\": 74327,\n" +
                "    \"ep_id\": 259620,\n" +
                "    \"favorites\": 227002,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545962400,\n" +
                "    \"lastupdate_at\": \"2018-12-28 10:00:00\",\n" +
                "    \"isNew\": true,\n" +
                "    \"play_count\": 3184418,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 25567,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/5b363116e010b8ce4e273d2243753173c31a5697.jpg\",\n" +
                "    \"title\": \"通灵妃\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 736241,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"总集篇9\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/1fb653e1e9a825ef1487216c834d3f72c647a8aa.jpg\",\n" +
                "    \"danmaku_count\": 523487,\n" +
                "    \"ep_id\": 259464,\n" +
                "    \"favorites\": 736241,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545962400,\n" +
                "    \"lastupdate_at\": \"2018-12-28 10:00:00\",\n" +
                "    \"isNew\": true,\n" +
                "    \"play_count\": 23586741,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 24438,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/0a09c5fc3b66bfe52b335d62b5af9d4748697a7d.jpg\",\n" +
                "    \"title\": \"小绿和小蓝\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": -1\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 317,\n" +
                "    \"attention\": 631349,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"110\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/8386e389b3d72850a1dedf8bdc83ae252fd11ebe.jpg\",\n" +
                "    \"danmaku_count\": 769601,\n" +
                "    \"ep_id\": 258810,\n" +
                "    \"favorites\": 631349,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545962400,\n" +
                "    \"lastupdate_at\": \"2018-12-28 10:00:00\",\n" +
                "    \"isNew\": true,\n" +
                "    \"play_count\": 73317543,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 6159,\n" +
                "    \"season_status\": 13,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/78216ddad76505257a76ea5ee92902bdf1b8fb10.jpg\",\n" +
                "    \"title\": \"妖神记\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 317,\n" +
                "    \"attention\": 352150,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"11\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/8f19c704a6516bab4716931db10621c1b6c6e4bf.jpg\",\n" +
                "    \"danmaku_count\": 105072,\n" +
                "    \"ep_id\": 258700,\n" +
                "    \"favorites\": 352150,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545883200,\n" +
                "    \"lastupdate_at\": \"2018-12-27 12:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 8263731,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 25823,\n" +
                "    \"season_status\": 13,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/67a851a5cd87fef2ddd6f9bd6cf691781a1d8b95.jpg\",\n" +
                "    \"title\": \"画江湖之不良人 第三季\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 12083,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"4\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/8df12fc092928ba57069eb16e077f9d719f345e5.jpg\",\n" +
                "    \"danmaku_count\": 10022,\n" +
                "    \"ep_id\": 258686,\n" +
                "    \"favorites\": 12083,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545876000,\n" +
                "    \"lastupdate_at\": \"2018-12-27 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 486760,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 26176,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/c96b4daa6f3f1925184bc7835a6a26ee573d6970.jpg\",\n" +
                "    \"title\": \"通灵妃 河南话版\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 151420,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"10\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/57dc8fdf8373f8c1facfc2911d50be1cfc6da18a.jpg\",\n" +
                "    \"danmaku_count\": 26706,\n" +
                "    \"ep_id\": 258689,\n" +
                "    \"favorites\": 151420,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545876000,\n" +
                "    \"lastupdate_at\": \"2018-12-27 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 2023158,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 24888,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/ef2f039b71564cc469b2cac9d8f2879e1259a74f.jpg\",\n" +
                "    \"title\": \"山河社稷图\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 4\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 317,\n" +
                "    \"attention\": 91054,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"12\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/853afc1e8cbba2f6395d4ae1bfbcd3d9b6beb27a.jpg\",\n" +
                "    \"danmaku_count\": 11536,\n" +
                "    \"ep_id\": 258667,\n" +
                "    \"favorites\": 91054,\n" +
                "    \"is_finish\": 1,\n" +
                "    \"lastupdate\": 1545796800,\n" +
                "    \"lastupdate_at\": \"2018-12-26 12:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 1086310,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 25588,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/bd0d3fd43676a87853a9490c4fe5a6c070d23e1b.jpg\",\n" +
                "    \"title\": \"战斗吧歌姬 第一季\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 317,\n" +
                "    \"attention\": 9328,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"4\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/ca07e1b35d71278a6c1506f93a30a326171af628.jpg\",\n" +
                "    \"danmaku_count\": 7179,\n" +
                "    \"ep_id\": 258671,\n" +
                "    \"favorites\": 9328,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545789600,\n" +
                "    \"lastupdate_at\": \"2018-12-26 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 528594,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 26177,\n" +
                "    \"season_status\": 2,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/c96b4daa6f3f1925184bc7835a6a26ee573d6970.jpg\",\n" +
                "    \"title\": \"通灵妃 东北话版\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 317,\n" +
                "    \"attention\": 197291,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"9\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/49809d0b8a29403a2794d2b6bf1b75f5ced2fd09.jpg\",\n" +
                "    \"danmaku_count\": 45358,\n" +
                "    \"ep_id\": 258959,\n" +
                "    \"favorites\": 197291,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545789600,\n" +
                "    \"lastupdate_at\": \"2018-12-26 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 1765086,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 24710,\n" +
                "    \"season_status\": 13,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/0ed1b9a1f20cc7e7638dc13dbac7dc2a553a3274.jpg\",\n" +
                "    \"title\": \"我是江小白 第二季\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 352222,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"10\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/e0bded09ccbefa5667c0697741842dbdd03e5d72.jpg\",\n" +
                "    \"danmaku_count\": 23329,\n" +
                "    \"ep_id\": 257438,\n" +
                "    \"favorites\": 352222,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545789600,\n" +
                "    \"lastupdate_at\": \"2018-12-26 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 5255148,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 24638,\n" +
                "    \"season_status\": 13,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/4426ee9a3e8a628d361ef68de91567d092930be9.jpg\",\n" +
                "    \"title\": \"我家大师兄脑子有坑 特别篇\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 3\n" +
                "  },\n" +
                "  {\n" +
                "    \"area\": \"中国大陆\",\n" +
                "    \"arealimit\": 316,\n" +
                "    \"attention\": 162055,\n" +
                "    \"bangumi_id\": 0,\n" +
                "    \"nowEpisode\": \"3\",\n" +
                "    \"cover\": \"http://i0.hdslb.com/bfs/bangumi/6cd0e3278712e04aef36723e44a805c432bc5c61.jpg\",\n" +
                "    \"danmaku_count\": 17657,\n" +
                "    \"ep_id\": 259015,\n" +
                "    \"favorites\": 162055,\n" +
                "    \"is_finish\": 0,\n" +
                "    \"lastupdate\": 1545789600,\n" +
                "    \"lastupdate_at\": \"2018-12-26 10:00:00\",\n" +
                "    \"isNew\": false,\n" +
                "    \"play_count\": 1001878,\n" +
                "    \"pub_time\": \"\",\n" +
                "    \"season_id\": 24608,\n" +
                "    \"season_status\": 13,\n" +
                "    \"spid\": 0,\n" +
                "    \"coverImage\": \"http://i0.hdslb.com/bfs/bangumi/100366a97d34dd5e5c92d160e26d027ccec9b640.jpg\",\n" +
                "    \"title\": \"少年歌行\",\n" +
                "    \"viewRank\": 0,\n" +
                "    \"weekday\": 3\n" +
                "  }]";

        jedis.set(RedisKeyEnum.MANGA_LIST.getValue(),str);
        List<Manga> list = JSON.parseArray( jedis.get(RedisKeyEnum.MANGA_LIST.getValue()),Manga.class) ;
        System.out.println(list.get(0).getArea());

    }

    /**
     * @description 管道测试 将所有命令一次性发出
     * @author CF create on 2018/3/17 18:55
     */
    private static void pipeLineTest(Jedis jedis) {
        Pipeline pl = jedis.pipelined();

        System.out.println(pl.isInMulti());
        pl.set("pipe-test01", "01");
        pl.set("pipe-test02", "02");
        pl.hset("pipe-test03", "03", "03");
        List list = pl.syncAndReturnAll();
        System.out.println(list.toString());
    }

    /**
     * @description 事务测试 保证某些redis操作必然成功
     * 在客户端做这些并发严重的场景下性能很差  会产生大量无用的重试
     * 最好使用分布式锁 或者脚本
     * @author CF create on 2018/3/17 18:07
     */
    private static void transTest(Jedis jedis) {
        while ("OK".equals(jedis.watch("tras-cc-test"))) {
            try {
                Transaction trans = jedis.multi();
                trans.set("tras-cc-test", "lalalla");

                trans.set("tras-cc-test", "djfsdkjfjk");
                //只要提交前有任何对此key的更改发生 返回的result就会变为空
                // 相反则返回对应操作的成功默认值
                List result = trans.exec();
                if (CollectionUtils.isEmpty(result)) {
                    jedis.unwatch();
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static void publishTest(Jedis jedis) {
        jedis.publish("cc-pb-test-01", " pubsub test lalalal");
        jedis.close();

    }

    private static void subscribeTest(Jedis jedis) {
        CCTestPubSub sub = new CCTestPubSub();
        jedis.subscribe(sub, "cc-pb-test-01");

    }

    private static void hsetTest(Jedis jedis) {
        //        jedis.hget("cc", "cc");

        String str = "{\n" +
                "\"stockCode\":\"300573\",\n" +
                "\"stockName\":\"建设银行\",\n" +
                "\"nowPrice\":\"32.52\",\n" +
                "\"yesPrice\":\"32.78\",\n" +
                "\"status\":\"1\",\n" +
                "\"stockFullCode\":\"SZ300573\"\n" +
                "}";
        String str2 = jedis.hget("STOCK_CODE_LIST", "ALL_MARKET_CODE");
        jedis.hset("PHOENIX_STOCK_CODE_LIST", "ALL_MARKET_CODE", str2);
/*        JedisPoolConfig config = initConfig();
        JedisPool jedisPool2 = new JedisPool(config, "116.62.21.135", 6379, RedisConstants.TIMEOUT, "redis_dev_1234");
        Jedis jedis = jedisPool2.getResource();
        System.out.println(jedis.get("cc"));*/
    }

    private static void incrTest(Jedis jedis) {
        String hourKey = "PHOENIX_SEND_VERIFY_COUNT_201709211815270000011001_13758080963";
        String dayKey = "PHOENIX_SEND_VERIFY_TOTAL_COUNT_201709211815270000011001_13758080963";
        String hourCou = jedis.get(hourKey);
        String dayCou = jedis.get(dayKey);
        int count = StringUtils.isEmpty(hourCou) ? 0 : Integer.parseInt(hourCou);
        int totalCount = StringUtils.isEmpty(dayCou) ? 0 : Integer.parseInt(dayCou);
        jedis.incr(hourKey);
        jedis.incr(dayKey);
        jedis.close();
    }


}
