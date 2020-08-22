package com.qunhe.toilet.core.common.config.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.qunhe.toilet.core.common.settings.SettingsEnum;
import com.qunhe.toilet.core.common.settings.SettingsHolder;
import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 14:46.
 */
//@SpringBootConfiguration
//@ConditionalOnBean(SettingsPubSubRelInit.class)
//@Component
public class RocketMqConsumerConfig {
    public static final Logger logger = LoggerFactory.getLogger(RocketMqConsumerConfig.class);
    /**
     *mq的nameserver地址
     */
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    /**
     *
     */
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    /**
     *
     */
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    /**
     *
     */
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    /**
     *
     */
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;
    @Autowired
    private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

    @Bean
    public DefaultMQPushConsumer getRocketConsumer(){
        logger.info("default consumer namesrvAddr= "+namesrvAddr);
        this.namesrvAddr= SettingsHolder.getProperty(SettingsEnum.ROCKETMQ_PRUDUCER_NAME_SERVER);
        logger.info("custom consumer namesrvAddr= "+namesrvAddr);
        AssertUtil.isNullStr(this.groupName,"getRocketConsumer consumer groupName isEmpty");
        AssertUtil.isNullStr(this.namesrvAddr,"getRocketConsumer consumer namesrvAddr isEmpty");
        AssertUtil.isNullStr(this.topics,"getRocketConsumer topics isEmpty");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        //设置监听器
        consumer.registerMessageListener(mqMessageListenerProcessor);
        /*
         * 设置消费模型，集群还是广播，默认为集群
         */
        //consumer.setMessageModel(MessageModel.CLUSTERING);

        /*
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        try {
            /*
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，
             * 则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0],topicTag[1]);
            }
            consumer.start();
            logger.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr);
        }catch (MQClientException e){
            logger.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr,e);
            throw new BusinessException(e.getMessage());
        }
        return consumer;

    }

}
