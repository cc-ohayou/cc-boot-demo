package com.cc.ccbootdemo.core.service;

import com.cc.ccbootdemo.facade.domain.dataobject.User;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:42.
 */
public interface UserService {
    List<User> getUserList(User params);
    void addUser(User params);
}
