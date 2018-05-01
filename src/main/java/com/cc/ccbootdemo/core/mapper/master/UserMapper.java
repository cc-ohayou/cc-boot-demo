package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.dataobject.City;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 15:19.
 */
//@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User>{

    /**
     * 根据城市名称，查询城市信息
     *
     * @param realName 城市名
     */
    List<User> findByName(@Param("userName") String realName);

    List<User> getAllUserList();
}
