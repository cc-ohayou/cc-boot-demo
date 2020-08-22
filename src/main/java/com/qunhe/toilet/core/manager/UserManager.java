package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.bizobject.UserInfo;
import com.qunhe.toilet.facade.domain.dataobject.SessionDO;
import com.qunhe.toilet.facade.domain.dataobject.User;
import com.qunhe.toilet.facade.domain.dataobject.UserAttachDO;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:44.
 */
public interface UserManager {
       List<UserInfo> getAllUserList(User params);
       int addUser(User params);


    UserInfo getUserInfo(String userName);

    int updateUserInfoSelective(User pojo);

    UserInfo getUserInfoByUid(String userId);

    SessionDO getUserSession(SessionDO param);

    void updateSessionByExample(SessionDO sessionDO, Example example);

    /**
     * @description
     * @author CF create on 2019/1/15 14:41
     */
    String addSession(String uid,String mid,String source) ;

    void updateUserAttachInfoSelective(UserAttachDO user);

    UserInfo getUserInfoByMail(String mail);

    void loginOut(String userId);
}
