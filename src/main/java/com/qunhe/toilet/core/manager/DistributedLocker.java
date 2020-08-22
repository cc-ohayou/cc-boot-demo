package com.qunhe.toilet.core.manager;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * @author Administrator
 */
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}