package com.qunhe.toilet.facade.domain.common.util;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 13:32.
 */
@Slf4j
public class RetryUtil {
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("retryUtil-pool-%d").build();

    /**
     * 可缓存线程执行器(依jvm情况自行回收创建)
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(0, 500,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),namedThreadFactory);

    /**
     * 默认方法(3秒超时,重试3次)
     * @param callable
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public static Boolean execute(Callable callable) throws InterruptedException, ExecutionException, TimeoutException {
        return execute(callable,2000,1000,2);
    }

    /**
     * 方法超时控制
     * @param callable 方法体
     * @param timeout 超时时长
     * @param interval 间隔时长
     * @param retryTimes 重试次数
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static Boolean execute(Callable<Boolean> callable, long timeout,long interval, int retryTimes) throws ExecutionException, InterruptedException, TimeoutException {
        Boolean result = false;
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        executorService.execute(futureTask);
        try {
            result = futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            log.error("retryUtil execute InterruptedException ",e);
            futureTask.cancel(true);
            throw e;
        }catch(TimeoutException e){
            futureTask.cancel(true);
          /*  callable.getFlag().waitForEnd();
            if(callable.getFlag().isNormaled()){
                return true;
            }*/
            log.error("retryUtil execute time out ",e);

            //超时重试
            retryTimes--;
            if(retryTimes > 0){
                Thread.sleep(interval);
                execute(callable,timeout,interval,retryTimes);
            }else{
                throw e;
            }
        }
        return result;
    }
}
