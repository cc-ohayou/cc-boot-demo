package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.PushManager;
import com.cc.ccbootdemo.core.mapper.master.UserPushDOMapper;
import com.cc.ccbootdemo.facade.domain.bizobject.msg.PushMsgMap;
import com.cc.ccbootdemo.facade.domain.common.constants.CommonConstants;
import com.cc.ccbootdemo.facade.domain.common.exception.ExceptionCode;
import com.cc.ccbootdemo.facade.domain.common.util.AssertUtil;
import com.cc.ccbootdemo.facade.domain.common.util.DateUtil;
import com.cc.ccbootdemo.facade.domain.common.util.push.PushReq;
import com.cc.ccbootdemo.facade.domain.common.util.push.PushReturnData;
import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gexin.fastjson.JSON;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 15:27.
 */
@Component
public class PushManagerImpl implements PushManager {
    private  Logger logger = LoggerFactory.getLogger(PushManagerImpl.class);
    private static final JsonNodeFactory factory = JsonNodeFactory.instance;
    private static ExecutorService pushPool = initPool("pushMsg");
    @Resource
    UserPushDOMapper userPushMapper;
    private static ExecutorService initPool(String type) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(type + "-pool-%d").build();
//        Executors.newCachedThreadPool();
        return new ThreadPoolExecutor(0, 1000,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                namedThreadFactory);
//                new MyThreadTaskAbortPolicy(logger));
    }


    @Override
    public  void geTuiPush(PushReq req, PushConfigDO pushConfigDO) {
        try {
//            pushMsg(req);
            pushPool.execute(() -> pushMsg(req,pushConfigDO));
        } catch (Exception e) {
            logger.warn("geTuiPush error  pushPool.execute exception", e);
        }
    }

    private  void pushMsg(PushReq req,PushConfigDO pushConfig) {
        PushMsgMap msgMap = req.getMsgMap();
        if (msgMapCheckPassed(msgMap)&&!CollectionUtils.isEmpty(req.getTargetUserIds())) {
            for (int i = 0; i < req.getTargetUserIds().size(); i++) {
                try {
                    doPushWork(req.getMsgMap(),req.getTargetUserIds().get(i),pushConfig);
                } catch (RequestException e) {
                    logger.warn("geTuiPush error,targetUserId="+req.getTargetUserIds().get(i), e);
                }
            }
        }
    }

    private  boolean msgMapCheckPassed(PushMsgMap msgMap) {
        boolean flag = true;
        try {
            AssertUtil.isNullStr(msgMap.getTitle(), ExceptionCode.PARAM_ERROR, "推送参数非法");
            AssertUtil.isNullStr(msgMap.getType(), ExceptionCode.PARAM_ERROR, "推送参数非法");
            AssertUtil.isNullStr(msgMap.getContent(), ExceptionCode.PARAM_ERROR, "推送参数非法");
            AssertUtil.isNullStr(msgMap.getMerchantId(), ExceptionCode.PARAM_ERROR, "推送参数非法");
        } catch (Exception e) {
            logger.warn("push param illegal empty");
            flag = false;
        }
        return flag;
    }

    private  void doPushWork(PushMsgMap msgMap,String targetUserId,PushConfigDO pushConfig) {
        msgMap.setTargetUserId(targetUserId);
        //此处使用targetUserId和商户id进行查询 cids可能有多个 不同客户端都有效 都推送
        List<String> cids = userPushMapper.selectCids(msgMap);
        PushReturnData data = new PushReturnData();
        data.setContent(msgMap.getContent());
        data.setMsgType(msgMap.getType());
        data.setTitle(msgMap.getTitle());
        if (!CollectionUtils.isEmpty(cids) && pushConfig != null) {
            IGtPush push = new IGtPush(CommonConstants.GETUI_host, pushConfig.getAppKey(), pushConfig.getMasterSecret());
            IBatch batch = push.getBatch();
            for (String cid : cids) {
                try {
                    constructClientTransMsg(pushConfig, data, batch, cid);
                    IPushResult result = batch.submit();
                    logger.info("#####push-result=" + JSON.toJSONString(result)+",targetUserId="+targetUserId);
                } catch (Exception e) {
                    logger.warn("message sending failed,cid=" + cid, e);
                }
            }
        } else {
            logger.warn("!!!####push cids is empty or pushConfigDO not exists");
        }



    }




    private  void constructClientTransMsg(PushConfigDO pushConfigDO, PushReturnData data, IBatch batch, String cid) throws Exception {

        SingleMessage message = new SingleMessage();
        //透传模板
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(pushConfigDO.getAppId());
        template.setAppkey(pushConfigDO.getAppKey());
        template.setTransmissionContent(JSON.toJSONString(data));
        template.setTransmissionType(2); // 这个Type为int型 1标识自动打开app 2是app自定义打开
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        /*payload.setCategory("$由客户端定义");*/
       /* //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("xxx订阅了您的策略"));*/

        //字典模式使用APNPayload.DictionaryAlertMsg
        payload.setAlertMsg(getDictionaryAlertMsg(data));
        payload.addCustomMsg("msgData",data);
        // 添加多媒体资源
       /* payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
                .setOnlyWifi(true));*/

        template.setAPNInfo(payload);
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 1000 * 1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(pushConfigDO.getAppId());
        target.setClientId(cid);
        batch.add(message, target);
    }

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(PushReturnData data) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(data.getContent());
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey(data.getContent());//
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(data.getTitle());
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }

    private void setExtInfo(ObjectNode ext, Map<String, String> msgMap) {
        for (Map.Entry<String, String> element : msgMap.entrySet()) {
            ext.put(element.getKey(), element.getValue());
        }
        ext.put("time", DateUtil.standardFormat(new Date()));
        msgMap.put("em_push_title", msgMap.get("msg"));
        // 必加字段这样ios收到的信息开头不会有冒号
        ObjectNode emApnsExt = factory.objectNode();
        emApnsExt.put("em_push_title", msgMap.get("msg"));
        ext.set("em_apns_ext", emApnsExt);
    }

}

