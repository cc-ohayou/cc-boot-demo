package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.dataobject.SessionDO;
import tk.mybatis.mapper.common.Mapper;

public interface SessionDOMapper extends Mapper<SessionDO> {
    /**
     * 通过用户id获取session信息
     *
     * @param userId
     */
    SessionDO queryByUserId(String userId);
}