package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.manager.UserManager;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:43.
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserManager userManager;

    @Override
    public List<User> getUserList(User params) {

        return userManager.getAllUserList(params);
    }
}
