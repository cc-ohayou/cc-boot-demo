package com.cc.ccbootdemo.facade.domain.ThreadLocals;

/**
 * @AUTHOR CF 余额对应的线程本地分布式锁标识
 * @DATE Created on 2018/3/23 15:53.
 */
public class BalanceRedisLockSign extends BaseLockSign{

    private  static ThreadLocal<Boolean> lockSign=new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    @Override
    public  boolean getLockSign() {

        return lockSign.get();
    }

    @Override
    public String getLockLocalName() {
        return "BalanceRedisLockSign";
    }

    @Override
    public  void setLockSign(boolean sign) {
        lockSign.set(sign);
    }
    @Override
    public  void clear() {
        lockSign.remove();
    }

}
