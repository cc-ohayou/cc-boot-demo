package com.qunhe.toilet.facade.domain.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


}
