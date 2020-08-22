package com.qunhe.toilet.core.common.settings;

import com.google.common.collect.Sets;
import com.qunhe.toilet.core.manager.RedisManager;
import com.qunhe.toilet.core.service.IntelligentToiletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/7 15:13.
 */
@Component
public class ToiletPitPubSub extends JedisPubSub {
    private Logger logger= LoggerFactory.getLogger(ToiletPitPubSub.class);
    @Resource
    IntelligentToiletService intelligentToiletService;


    public void logInfo(String msg){
        logger.info("###SettingsPubSub {}",msg);
    }

    public void logWarn(String msg, Exception e){
        logger.warn("###SettingsPubSub {}",msg,e);
    }


    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        logInfo("toilet-pit-find onMessage received channel=" + channel + " message=" + message);
        try{
            if(RedisChannel.TOILET_FIND.getValue().equals(channel)) {
                intelligentToiletService.doSubPitEvent(message);
            }
        }catch(Exception e){
            logWarn("###SettingsPubSub onMessage failed",e);

        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        super.onPMessage(pattern, channel, message);

    }




    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
        logInfo("onSubscribe  " + channel + " subscribedChannels=" + subscribedChannels);


    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

        logInfo("onPSubscribe  pattern=" + pattern + " subscribedChannels=" + subscribedChannels);

    }



}
