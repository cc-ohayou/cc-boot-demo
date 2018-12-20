package com.cc.ccbootdemo.core.service;

import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.dataobject.User;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:42.
 */
public interface UserService {
    List<User> getUserList(User params);
    void addUser(User params);

    void pushMsg();

    /**
     * @description 发送消息 返回msgId
     * @author CF create on 2018/12/7 17:02
     */
    String  produce(MQProducerParam param);

    String getDownloadUrl();
}
