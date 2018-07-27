package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.dataobject.PushConfigDO;
import tk.mybatis.mapper.common.Mapper;

public interface PushConfigDOMapper extends Mapper<PushConfigDO> {
    /**
     * @description
     * @author CF create on 2018/7/10 17:57
     */
    PushConfigDO queryConfig(String mid);
}