package com.cc.ccbootdemo.core.manager;

import com.cc.ccbootdemo.facade.domain.common.util.push.PushReq;
import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 18:42.
 */
public interface PushManager {

    void geTuiPush(PushReq req, PushConfigDO pushConfigDO);

}
