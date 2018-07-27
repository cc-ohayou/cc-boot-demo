package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.UserManager;
import com.cc.ccbootdemo.core.mapper.master.UserMapper;
import com.cc.ccbootdemo.facade.domain.bizobject.strgy.StrgyBiz;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:44.
 */
@Component
public class UserManagerImpl implements UserManager{
    @Resource
    UserMapper userMapper;
    @Override
    public List<User> getAllUserList(User params) {
        return userMapper.getAllUserList();
    }

    @Override
    public int addUser(User params) {
       return userMapper.insert(params);
    }

    @Override
    public List<StrgyBiz> getStrgyList(Set<String> uids) {
        return null;
    }
}
