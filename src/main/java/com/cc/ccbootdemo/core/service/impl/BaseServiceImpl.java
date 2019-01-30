package com.cc.ccbootdemo.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.ccbootdemo.core.manager.*;
import com.cc.ccbootdemo.core.mapper.master.PushConfigDOMapper;
import com.cc.ccbootdemo.core.mapper.master.UserPushDOMapper;
import com.cc.ccbootdemo.core.service.BaseService;
import com.cc.ccbootdemo.facade.domain.bizobject.msg.PushMsgMap;
import com.cc.ccbootdemo.facade.domain.bizobject.param.SearchBaseParam;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.exception.ExceptionCode;
import com.cc.ccbootdemo.facade.domain.common.handler.MyThreadTaskAbortPolicy;
import com.cc.ccbootdemo.facade.domain.common.util.HttpClientUtil;
import com.cc.ccbootdemo.facade.domain.common.util.PsPage;
import com.cc.ccbootdemo.facade.domain.common.util.push.PushReq;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    PushConfigDOMapper pushConfigMapper;
    @Resource
    UserPushDOMapper userPushMapper;
    @Resource
    PushManager pushManager;
    @Resource
    UserManager userManager;
    @Resource
    RedisManager redisManager;
    @Resource
    MqManager mqManager;
    @Resource
    OperateBizManager operateBizManager;
    @Resource
    MailManager mailManager;


    //商户推送配置  用到时初始化一次
    private static Map<String,PushConfigDO> pushConfigDO =new ConcurrentHashMap<>(8);
//    PushConfigDO pushConfig=

    /**
     * @description 获取对应商户的推送配置
     * @author CF create on 2018/3/30 17:28
     */
    private PushConfigDO getPushConfigByMerchantId(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            logger.warn("getPushConfigByMerchantId error mid is empty");
            throw new BusinessException(ExceptionCode.BIZ_ERROR, "获取推送配置时,商户id不能为空");
        }
        if (pushConfigDO.get(merchantId) == null) {
            initConfig(merchantId);
        }
        return pushConfigDO.get(merchantId);
    }

    /**
     * @description 初始化对应商户的推送配置
     * @author CF create on 2018/3/30 17:28
     */
    private  void initConfig(String merchantId) {
        PushConfigDO config = pushConfigMapper.queryConfig(merchantId);
        if (config == null) {
            logger.warn("initConfig error mid config is null mid=" + merchantId);
            throw new BusinessException(ExceptionCode.BIZ_ERROR, "商户个推配置异常");
        } else {
            pushConfigDO.put(merchantId, config);
        }
    }


    void pushGeTuiMsg(List<String> targetUids,String content,String mid){
        PushConfigDO pushConfig=getPushConfigByMerchantId(mid);
        PushReq req=new PushReq();
        req.setTargetUserIds(targetUids);
        PushMsgMap  msgMap= new PushMsgMap();
        msgMap.setTitle("策略提醒");
        msgMap.setMerchantId(mid);
        msgMap.setType("0");
        msgMap.setContent(content);
        req.setMsgMap(msgMap);
        pushManager.geTuiPush(req,pushConfig);
    }

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



    /**
     * @description   请求ps接口
     * @author CF create on 2018/11/20 13:41
     */
    String postReq(OperateBiz bizDetail) {

        Map<String, String> param=new HashMap<>(4);
        Map<String, String> headerMap=new HashMap<>(2);
        headerMap.put("specialToken","0070086743qwe");
        if(!StringUtils.isEmpty(bizDetail.getReqParamStr())){
            param=JSONObject.parseObject(bizDetail.getReqParamStr(),Map.class);
        }

        String result = "";
//        String hostUrl="http://localhost:8088";
        try {
            logger.info("项目:{},环境：{}，url：{}，desc:{},param:{}",bizDetail.getProject(),
                    bizDetail.getEnvType(),bizDetail.getUrl(),bizDetail.getDesc(),param);
            result = HttpClientUtil.sendPost(bizDetail.getUrl(),
                    param, headerMap, null);
            logger.info("返回结果result:{}",result);
        } catch (Exception ex) {
            logger.warn("调用gateWay接口异常,httpReq exception", ex);
            throwBusinessException("调用ps" + "+describe+" + "接口异常,httpReq exception");
        }
        return judgePostResult(result);
    }


    Map<String, String> initPostHeaderMap(String merchantId , String userId, int cap) {
        Map<String, String> paramMap = new HashMap(cap);
        paramMap.put("merchantId", merchantId);
        paramMap.put("userId", userId);
        return paramMap;
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
