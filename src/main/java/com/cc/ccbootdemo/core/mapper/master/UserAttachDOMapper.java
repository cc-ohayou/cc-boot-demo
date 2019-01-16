package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.dataobject.UserAttachDO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserAttachDOMapper extends Mapper<UserAttachDO> {


    int insertList(@Param("pojos") List<UserAttachDO> pojo);


    int updateSelective(@Param("pojo") UserAttachDO pojo);

}
