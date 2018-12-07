package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.manager.MqManager;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.manager.UserManager;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.bizobject.strgy.StrgyBiz;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.common.util.AssertUtil;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:43.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    UserManager userManager;
    @Resource
    RedisManager  redisManager;
    @Resource
    MqManager mqManager;


    @Override
    public List<User> getUserList(User params) {
        logger.info("params="+params);
        return userManager.getAllUserList(params);
    }

    @Override
    public void addUser(User params) {
        userManager.addUser(params);
    }

    @Override
    public void pushMsg() {
        Set<String> uids=redisManager.smembers(RedisConstants.SPECIAL_MEN_UID);
        Set<String> targetUids=redisManager.smembers(RedisConstants.SPECIAL_TARGET_MEN_UID);
        //此处使用targetUserId和商户id进行查询 cids可能有多个 不同客户端都有效 都推送
        //获取当前时间和上次间隔时间新建策略
        Map<String,Object> params=new HashMap<>();
        params.put("uids",uids);
        params.put("lastTime",redisManager.get(RedisConstants.LAST_PUSH_TIME));
        List<StrgyBiz>  strgys=userManager.getStrgyList(uids);
        for (int i = 0; i < strgys.size(); i++) {
            pushGeTuiMsg(new ArrayList<>(targetUids),strgys.get(i).toString(),"000");
        }

    }

    @Override
    public String produce(MQProducerParam param) {
        AssertUtil.isNullParamStr(param.getTopic(),"发送消息主题不可为空");
        AssertUtil.isNullParamStr(param.getTags(),"发送消息tag格式不可为空");
        AssertUtil.isNullParamStr(param.getMessage(),"发送消息内容不可为空");
        return mqManager.produceMsg(param);
    }
}
