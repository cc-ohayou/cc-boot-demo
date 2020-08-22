package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.common.param.MQProducerParam;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 17:04.
 */
public interface MqManager {

    String produceMsg(MQProducerParam param);
}
