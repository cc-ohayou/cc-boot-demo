package com.cc.ccbootdemo.core.common.config.mq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.cc.ccbootdemo.core.common.settings.SettingsEnum;
import com.cc.ccbootdemo.core.common.settings.SettingsHolder;
import com.cc.ccbootdemo.core.common.settings.SettingsPubSubRelInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 14:47.
 */
@Component
@ConditionalOnBean(SettingsPubSubRelInit.class)
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumeMsgListenerProcessor.class);

    /**
     *  默认msgs里只有一条消息，
     *  可以通过设置consumeMessageBatchMaxSize参数来批量接收消息<br/>
     *  不要抛异常，如果没有return CONSUME_SUCCESS ，
     *  consumer会重新消费该消息，直到return CONSUME_SUCCESS
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if(CollectionUtils.isEmpty(msgs)){
            logger.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);
        logger.info("接受到的消息为："+messageExt.toString());
        String topic=SettingsHolder.getProperty(SettingsEnum.ROCKETMQ_TEST_TOPIC01);
        String tag=SettingsHolder.getProperty(SettingsEnum.ROCKETMQ_TEST_TAG01);
        //根据tag和topic进行不同的业务逻辑处理
        if(topic.equals(messageExt.getTopic())){
            if(tag.equals(messageExt.getTags())){
                //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，
                // 如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
                //TODO 获取该消息重试次数
                int reconsume = messageExt.getReconsumeTimes();
                logger.info("consumeMessage reConsumeTimes={}",reconsume);
                if(reconsume ==3){//消息已经重试了3次，如果不需要再次消费，则返回成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //TODO 处理对应的业务逻辑
                System.out.printf("!!!consumeMessage topic and tag match!!! msgId=%s, content=%s , topic=%s ,tag=%s %n",
                        messageExt.getMsgId(), Arrays.toString(messageExt.getBody()),topic,tag);
            }else{
                int reconsume = messageExt.getReconsumeTimes();
                logger.info("consumeMessage reConsumeTimes={}",reconsume);
                if(reconsume ==3){//消息已经重试了3次，如果不需要再次消费，则返回成功
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //TODO 处理对应的业务逻辑
                System.out.printf("!!consumeMessage topic match tag not match!! msgId=%s, content=%s , topic=%s ,tag=%s %n",
                        messageExt.getMsgId(), Arrays.toString(messageExt.getBody()),topic,tag);
            }
        }else{
            int reconsume = messageExt.getReconsumeTimes();
            logger.info("consumeMessage reConsumeTimes={}",reconsume);
            if(reconsume ==3){//消息已经重试了3次，如果不需要再次消费，则返回成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            //TODO 处理对应的业务逻辑
            System.out.printf("!consumeMessage topic  not match! msgId=%s, content=%s , topic=%s ,tag=%s %n",
                    messageExt.getMsgId(), Arrays.toString(messageExt.getBody()),topic,tag);

        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}

