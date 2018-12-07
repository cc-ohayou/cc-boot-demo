package com.cc.ccbootdemo.core.manager;

import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 17:04.
 */
public interface MqManager {

    String produceMsg(MQProducerParam param);
}
