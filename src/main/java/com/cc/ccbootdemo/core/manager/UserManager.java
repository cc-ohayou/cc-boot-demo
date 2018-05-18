package com.cc.ccbootdemo.core.manager;

import com.cc.ccbootdemo.facade.domain.dataobject.User;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:44.
 */
public interface UserManager {
       List<User> getAllUserList(User params);
       int addUser(User params);
}
