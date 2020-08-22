package com.qunhe.toilet.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qunhe.toilet.core.manager.*;
import com.qunhe.toilet.core.service.BaseService;
import com.qunhe.toilet.facade.domain.bizobject.param.SearchBaseParam;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.exception.ExceptionCode;
import com.qunhe.toilet.facade.domain.common.handler.MyThreadTaskAbortPolicy;
import com.qunhe.toilet.facade.domain.common.util.PsPage;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.qunhe.toilet.core.manager.MailManager;
import com.qunhe.toilet.core.manager.MqManager;
import com.qunhe.toilet.core.manager.RedisManager;
import com.qunhe.toilet.core.manager.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 17:53.
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService{
    private static Logger logger= LoggerFactory.getLogger(BaseServiceImpl.class);

    static ExecutorService specialPoolBase = initPool(1,4,"specialPool",logger);

    static ExecutorService initPool(int maxpoolSize, int coreSize, String type,Logger log) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(type + "-SettleTaskJob-pool-%d").build();
        return new ThreadPoolExecutor(maxpoolSize, coreSize,
                300L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10000),
                namedThreadFactory,
                new MyThreadTaskAbortPolicy(log, type));
    }

    @Resource
    UserManager userManager;
    @Resource
    RedisManager redisManager;
    @Resource
    MqManager mqManager;

    @Resource
    MailManager mailManager;




    /**
     * @description 初始化数据库分页查询请求参数
     * @author CF create on 2017/7/13 16:23
     */
     void initOffsetSizeInfo(SearchBaseParam param, int totalCount) {
        String pageSize = String.valueOf(param.getPageSize());
        int size;
        if (StringUtils.isEmpty(pageSize)) {
            size = PsPage.DEFAULT_PAGE_SIZE;
        } else {
            size = Integer.parseInt(pageSize);
        }
        int currPage = param.getCurrPage();
        int offset = (currPage - 1) * size;
        offset = offset > 0 ? offset : 0;
        offset = offset < totalCount ? offset : totalCount;
        param.setOffset(offset);
        param.setSize(size);
    }


    public void throwBusinessException(String msg) throws BusinessException {
        throw new BusinessException(ExceptionCode.BIZ_ERROR, msg);
    }






    String judgePostResult(String result) {
        // 验证返回结果
        if (StringUtils.isEmpty(result)) {
            logger.warn("调用gateWay接口异常 返回结果result:" + result);
            throwBusinessException("调用gateWay 接口异常,"+result);
        }
        /*
            result格式:
            int code;
            Object data;
            String msg;
         */
        JSONObject resultJson = JSON.parseObject(result);
        int code = resultJson.getIntValue("code");
        if (code != 0) {
            logger.warn("调用gateWay异常," + resultJson.toString());
            throwBusinessException(resultJson.getString("msg"));
        }
        return resultJson.getString("data");
    }


    public static void main(String[] args) {
        Long startTime=System.currentTimeMillis();
        FutureTask<String> futureTask=new FutureTask<>(() -> {
            try{
                Thread.sleep(5000);
                System.out.println("子线程计算任务:  执行完成!");

            }catch(Exception e){
                logger.error("邮箱验证码发送失败",e);
                return  "error";
            }
            return  "ok";
        });
       specialPoolBase.submit(futureTask);

        try {
            logger.info(Thread.currentThread().getName()+" futureTask.get()"+ futureTask.get());
//          超时时间小于2s或者是返回结果是error则一直等待
        while("error".equals(futureTask.get())||(System.currentTimeMillis()-startTime)<2000){
            logger.info("waiting for mail result ");
            logger.info("futureTask.get()"+ futureTask.get());
        }
        logger.info(Thread.currentThread().getName()+" futureTask.get()"+ futureTask.get());

        if("error".equals(futureTask.get())){
            futureTask.cancel(true);
            throw new BusinessException("发送邮件超时");
        }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
