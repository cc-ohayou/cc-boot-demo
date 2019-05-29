package com.cc.ccbootdemo.facade.domain.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/20 10:49.
 */
public class RedisLog   extends BaseLog{

    private Logger logger = LoggerFactory.getLogger(RedisLog.class);

    @Override
    public Logger getLogger() {
        return this.logger;
    }


    public static void main(String[] args) {
        Jedis jedis=new Jedis("47.96.130.48",6379);
        jedis.auth("redis-dev-4");
        System.out.println(jedis.get("cc"));
    }
}
