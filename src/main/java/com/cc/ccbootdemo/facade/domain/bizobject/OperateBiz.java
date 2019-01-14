package com.cc.ccbootdemo.facade.domain.bizobject;

import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.core.manager.impl.RedisManagerImpl;
import com.cc.ccbootdemo.facade.domain.common.constants.OperUrlPrefix;
import com.cc.ccbootdemo.facade.domain.common.enums.OperUrl;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyEnum;
import com.cc.ccbootdemo.facade.domain.common.util.DateUtil;
import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/10 10:40.
 */
@Data
public class OperateBiz {
    private String operId;
    //操作简称 比如 递延
    private String operName;
    //类型
    private String type;
    /**
     * 项目归属
     */
    private String project;
    private String desc;
    // 定时任务
    private String label;
    //环境类型
    private String envType;
    private String url;
    private String createTime;
    private String updateTime;

    public static void main(String[] args) {
        OperateBiz operateBiz=new OperateBiz();
        operateBiz.setOperId("001");
        operateBiz.setType("定时任务");
        operateBiz.setProject("ETF-TASK");
        operateBiz.setDesc("递延操作 请求后执行的是14:00递延定时逻辑");
        operateBiz.setOperName("递延");
        operateBiz.setLabel("定时任务");
        operateBiz.setEnvType("sit");
        operateBiz.setUrl(OperUrlPrefix.ETF_SIT_TASK+ OperUrl.ETF_TASK_DEFER.getValue());
        operateBiz.setCreateTime(DateUtil.standardFormat(new Date()));
        operateBiz.setUpdateTime(operateBiz.getCreateTime());

        OperateBiz operateBiz2=new OperateBiz();
        operateBiz2.setOperId("002");
        operateBiz2.setOperName("强平");
        operateBiz2.setProject("ETF-TASK");
        operateBiz2.setType("定时任务");
        operateBiz2.setDesc("定时任务强平 点击后进行到期日强平逻辑操作");
        operateBiz2.setLabel("定时任务");
        operateBiz2.setEnvType("sit");
        operateBiz2.setUrl(OperUrlPrefix.ETF_SIT_TASK+ OperUrl.ETF_TASK_FORCE_SELL.getValue());
        operateBiz2.setCreateTime(DateUtil.standardFormat(new Date()));
        operateBiz2.setUpdateTime(operateBiz.getCreateTime());
        Jedis jedis = RedisManagerImpl.getJedis();
        String str= JSON.toJSONString(operateBiz);
        jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str);

        String str2= JSON.toJSONString(operateBiz2);
        jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str2);

      /*  for (int i = 0; i <23 ; i++) {
            if(i%2==0){
                operateBiz.setOperId(String.valueOf((i)));
                String str= JSON.toJSONString(operateBiz);
                jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str);
            }else{
                operateBiz2.setOperId(String.valueOf((i)));
                String str= JSON.toJSONString(operateBiz2);
                jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str);
            }



        }*/
       /*List<String> list=jedis.lrange(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),10,19);
       List<OperateBiz> listBiz=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            listBiz.add(JSON.parseObject(list.get(i),OperateBiz.class));
        }
        System.out.println(listBiz);*/
    }
}
