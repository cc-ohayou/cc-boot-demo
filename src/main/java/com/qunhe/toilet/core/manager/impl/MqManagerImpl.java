package com.qunhe.toilet.core.manager.impl;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.qunhe.toilet.core.manager.MqManager;
import com.qunhe.toilet.facade.domain.common.param.MQProducerParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 17:04.
 */

@Component
public class MqManagerImpl implements MqManager {

    private Logger logger = LoggerFactory.getLogger(MqManagerImpl.class);
    private SendResult defaultResult = new SendResult();
    //    @Resource
//    MQProducer producer;
    static List myList = new ArrayList<>();


    {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        ExecutorService executorService3 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        scheduledExecutorService;
        ExecutorService executorService5 = Executors.newWorkStealingPool();


    }
//：20,143,488

    public static void main(String[] args) {
        System.out.println(0x7fffffff);
        System.out.println((Integer.MAX_VALUE));

        int[] a = {1, 233, 32, 45, 25, 6, 100, 34};
        Arrays.sort(a);

        for (int i = 0; i < 10000000; i++) {
            myList.add(i);
        }

        System.out.println("list over");
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String produceMsg(MQProducerParam param) {
        Message msg = getMessage(param);
        SendResult sendResult = defaultResult;
//        try {
            logger.info("test ");
//            producer.sendMessageInTransaction(msg,new MyLocalTransactionExecuterImpl(),"test");
//
//             sendResult =producer.send(msg, Long.parseLong(SettingsHolder.getProperty(SettingsEnum.ROCKETMQ_SEND_TIMEOUT)));

            logger.info("produceMsg sendResult=" + sendResult.toString());
//        } catch (MQClientException | InterruptedException | RemotingException | MQBrokerException e) {
//            logger.error("produceMsg error param=", param.toString(), e);
//        }
        return sendResult.getMsgId();
    }

    private Message getMessage(MQProducerParam param) {
        Message msg = new Message();
        msg.setTags(param.getTags());
        msg.setTopic(param.getTopic());
        msg.setBody(param.getMessage().getBytes());
        return msg;
    }

}

@Slf4j
class MyLocalTransactionExecuterImpl implements LocalTransactionExecuter {


    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {

        log.info("args is "+arg.toString()+" do sth with customized  args  " +
                "for example  do db work and then return  suitable LocalTransactionState to decide the mq" +
                "final commit or rollback  or " +
                " ");
        return null;
    }
}
