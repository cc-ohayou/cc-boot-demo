package com.cc.ccbootdemo.core.mapper.master;


import com.cc.ccbootdemo.facade.domain.bizobject.msg.PushMsgMap;
import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;
import com.cc.ccbootdemo.facade.domain.dataobject.UserPushDO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserPushDOMapper extends Mapper<UserPushDO> {
    /**
     * @description
     * @author CF create on 2018/7/10 18:22
     */
    List<String> selectCids(PushMsgMap map);

    PushConfigDO querySimplePushConfig(String merchantID);

    /**
     * @description 根据商户和设备个推client_id更新用户推送记录状态
     * @author CF create on 2018/4/9 15:44
     */
    void updateUserPushStatusByMidAndClientId(Map<String, Object> param);

}