package com.cc.ccbootdemo.core.task;

import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.common.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 18:16.
 */
@Component
@EnableScheduling
public class PushMsgJob implements SchedulingConfigurer {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisManager redisManager;

    private static final String cronKey = RedisConstants.PUSH_MSG_CRON;
    private static String cron;


    @Resource
    UserService userService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //lamda表达式 默认的接口Runnable被省略了 无参所以括号里面没有参数
        taskRegistrar.addTriggerTask(() -> {

            try {
//                userService.pushMsg();



            } catch (Exception e) {
                logger.error("cancelTradeJob-failed", e);
            }
        }, triggerContext -> {//lamda表达式 接口Trigger被省略了 有参数所以携带参数triggerContext  匿名内部类
            // 任务触发，可修改任务的执行周期  首先从redis获取定时规则  若key不存在则从properties文件中获取
            cron = redisManager.get(cronKey);
            if (null == cron || "".equals(cron)) {
                cron = String.valueOf("0 5 15 ? * *");
            }
            CronTrigger trigger = new CronTrigger(cron);//注意此处cron格式
            return trigger.nextExecutionTime(triggerContext);
        });
    }
}
