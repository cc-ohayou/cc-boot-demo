package com.cc.ccbootdemo.facade.domain.dataobject;

import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.core.manager.impl.RedisManagerImpl;
import com.cc.ccbootdemo.facade.domain.common.constants.OperUrlPrefix;
import com.cc.ccbootdemo.facade.domain.common.enums.OperUrl;
import com.cc.ccbootdemo.facade.domain.common.enums.redis.RedisKeyEnum;
import com.cc.ccbootdemo.facade.domain.common.util.DateUtil;
import com.cc.ccbootdemo.facade.domain.common.util.IdGen;
import lombok.Data;
import redis.clients.jedis.Jedis;

import javax.persistence.Table;
import java.util.Date;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/10 10:40.
 */
@Data
@Table(name = "operate_biz")
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
    /**
     * 操作所需权限
     */
    private String roleCode;

    public static void main(String[] args) {
        Jedis jedis = RedisManagerImpl.getJedis();
        OperateBiz operateBiz=new OperateBiz();
        operateBiz.setOperId(String.valueOf(IdGen.genId()));
//        operateBiz.setType("");
        operateBiz.setProject("etf-server");
        operateBiz.setDesc(OperUrl.INVESTOR_WARM_UP.getLabel());
        operateBiz.setOperName("消息配置刷新");
//        operateBiz.setLabel("消息配置");
        operateBiz.setEnvType("sit");
        operateBiz.setUrl(OperUrlPrefix.ETF_SIT_SERVER+ OperUrl.CLEAR_SUB_CONFIG.getValue());
        operateBiz.setCreateTime(DateUtil.standardFormat(new Date()));
        operateBiz.setUpdateTime(operateBiz.getCreateTime());

        String str= JSON.toJSONString(operateBiz);
        jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str);

        OperateBiz operateBiz2=new OperateBiz();
        operateBiz2.setOperId("006");
        operateBiz2.setOperName("消息订阅配置刷新");
        operateBiz2.setProject("phoenix-ps");
        operateBiz2.setType("配置");
        operateBiz2.setDesc(OperUrl.CLEAR_SUB_CONFIG.getLabel());
        operateBiz2.setLabel("消息配置");
        operateBiz2.setEnvType("dev");
        operateBiz2.setUrl(OperUrlPrefix.PS_DEV_SERVER+ OperUrl.CLEAR_SUB_CONFIG.getValue());
        operateBiz2.setCreateTime(DateUtil.standardFormat(new Date()));
        operateBiz2.setUpdateTime(operateBiz.getCreateTime());



        String str2= JSON.toJSONString(operateBiz2);
//        jedis.lpush(RedisKeyEnum.ETF_SIT_OPER_LIST.getValue(),str2);

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
