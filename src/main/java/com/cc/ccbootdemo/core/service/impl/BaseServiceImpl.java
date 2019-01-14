package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.manager.PushManager;
import com.cc.ccbootdemo.core.mapper.master.PushConfigDOMapper;
import com.cc.ccbootdemo.core.mapper.master.UserPushDOMapper;
import com.cc.ccbootdemo.core.service.BaseService;
import com.cc.ccbootdemo.facade.domain.bizobject.msg.PushMsgMap;
import com.cc.ccbootdemo.facade.domain.bizobject.param.SearchBaseParam;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.exception.ExceptionCode;
import com.cc.ccbootdemo.facade.domain.common.util.PsPage;
import com.cc.ccbootdemo.facade.domain.common.util.push.PushReq;
import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 17:53.
 */
@Service
public class BaseServiceImpl implements BaseService{

    @Resource
    PushConfigDOMapper pushConfigMapper;
    @Resource
    UserPushDOMapper userPushMapper;
    @Resource
    PushManager pushManager;


    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //商户推送配置  用到时初始化一次
    private static Map<String,PushConfigDO> pushConfigDO =new ConcurrentHashMap<>(8);
//    PushConfigDO pushConfig=

    /**
     * @description 获取对应商户的推送配置
     * @author CF create on 2018/3/30 17:28
     */
    private PushConfigDO getPushConfigByMerchantId(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            logger.warn("getPushConfigByMerchantId error mid is empty");
            throw new BusinessException(ExceptionCode.BIZ_ERROR, "获取推送配置时,商户id不能为空");
        }
        if (pushConfigDO.get(merchantId) == null) {
            initConfig(merchantId);
        }
        return pushConfigDO.get(merchantId);
    }

    /**
     * @description 初始化对应商户的推送配置
     * @author CF create on 2018/3/30 17:28
     */
    private  void initConfig(String merchantId) {
        PushConfigDO config = pushConfigMapper.queryConfig(merchantId);
        if (config == null) {
            logger.warn("initConfig error mid config is null mid=" + merchantId);
            throw new BusinessException(ExceptionCode.BIZ_ERROR, "商户个推配置异常");
        } else {
            pushConfigDO.put(merchantId, config);
        }
    }


    void pushGeTuiMsg(List<String> targetUids,String content,String mid){
        PushConfigDO pushConfig=getPushConfigByMerchantId(mid);
        PushReq req=new PushReq();
        req.setTargetUserIds(targetUids);
        PushMsgMap  msgMap= new PushMsgMap();
        msgMap.setTitle("策略提醒");
        msgMap.setMerchantId(mid);
        msgMap.setType("0");
        msgMap.setContent(content);
        req.setMsgMap(msgMap);
        pushManager.geTuiPush(req,pushConfig);
    }

    /**
     * @description 初始化数据库分页查询请求参数
     * @author CF create on 2017/7/13 16:23
     */
     void initOffsetSizeInfo(SearchBaseParam param, int totalCount) {
        String pageSize = String.valueOf(param.getPageSize());
        int size;
        if (StringUtils.isEmpty(pageSize)) {
            size = PsPage.DEFAULT_PAGE_SIZE;
        } else {
            size = Integer.parseInt(pageSize);
        }
        int currPage = param.getCurrPage();
        int offset = (currPage - 1) * size;
        offset = offset > 0 ? offset : 0;
        offset = offset < totalCount ? offset : totalCount;
        param.setOffset(offset);
        param.setSize(size);
    }


}
