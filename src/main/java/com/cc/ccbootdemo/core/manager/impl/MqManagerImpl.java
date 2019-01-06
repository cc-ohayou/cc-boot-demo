package com.cc.ccbootdemo.core.manager.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.manager.MqManager;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 17:04.
 */
@Component
public class MqManagerImpl implements MqManager{

    private Logger logger= LoggerFactory.getLogger(MqManagerImpl.class);
    private SendResult defaultResult=new SendResult();
    @Resource
    MQProducer producer;

    @Override
    public String produceMsg(MQProducerParam param) {
        Message msg = getMessage(param);
        SendResult sendResult=defaultResult;
        try {
             sendResult =producer.send(msg, Long.parseLong(SettingsHolder.getProperty(SettingsEnum.ROCKETMQ_SEND_TIMEOUT)));
             logger.info("produceMsg sendResult="+ sendResult.toString());
        } catch (MQClientException | InterruptedException | RemotingException | MQBrokerException e) {
            logger.error("produceMsg error param=",param.toString(),e);
        }
        return sendResult.getMsgId();
    }

    private Message getMessage(MQProducerParam param) {
        Message msg=new Message();
        msg.setTags(param.getTags());
        msg.setTopic(param.getTopic());
        msg.setBody(param.getMessage().getBytes());
        return msg;
    }
}
