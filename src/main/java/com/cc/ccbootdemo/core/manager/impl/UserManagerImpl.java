package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.UserManager;
import com.cc.ccbootdemo.core.mapper.master.SessionDOMapper;
import com.cc.ccbootdemo.core.mapper.master.UserAttachDOMapper;
import com.cc.ccbootdemo.core.mapper.master.UserMapper;
import com.cc.ccbootdemo.facade.domain.bizobject.UserInfo;
import com.cc.ccbootdemo.facade.domain.bizobject.strgy.StrgyBiz;
import com.cc.ccbootdemo.facade.domain.common.util.IdGen;
import com.cc.ccbootdemo.facade.domain.dataobject.SessionDO;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import com.cc.ccbootdemo.facade.domain.dataobject.UserAttachDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/25 19:44.
 */
@Component
public class UserManagerImpl extends BaseManagerImpl implements UserManager{
    public static final Integer SESSION_EXPIRED_DATE = 7;
    private static Logger logger= LoggerFactory.getLogger(UserManagerImpl.class);

    @Resource
    UserMapper userMapper;
    @Resource
    SessionDOMapper sessionDOMapper;

    @Resource
    UserAttachDOMapper userAttachDOMapper;

    @Override
    public List<UserInfo> getAllUserList(User params) {
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

    @Override
    public UserInfo getUserInfo(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public int updateUserInfoSelective(UserInfo pojo) {
        return userMapper.updateUserInfoSelective(pojo);
    }

    @Override
    public UserInfo getUserInfoByUid(String userId) {
        return userMapper.selectByUid(userId);
    }

    @Override
    public SessionDO getUserSession(SessionDO param) {
        return sessionDOMapper.selectOne(param);
    }

    @Override
    public void updateSessionByExample(SessionDO sessionDO, Example example) {
        sessionDOMapper.updateByExampleSelective(sessionDO, example);
    }


    @Override
    public String addSession(String uid,String mid,String source) {
        String sid = String.valueOf(IdGen.genId());
        //获取用户session信息
        SessionDO sessionDO = sessionDOMapper.queryByUserId(uid);
        int cou;
        if (sessionDO == null) {
            sessionDO = new SessionDO();
            sessionDO.setSessionId(String.valueOf(IdGen.genId()));
            sessionDO.setMerchantId(mid);
            sessionDO.setUserId(uid);
            sessionDO.setCreateTime(new Date());
            setSession(source, sid, sessionDO);
            cou = sessionDOMapper.insert(sessionDO);
        }else {
            setSession(source,sid,sessionDO);
            cou = sessionDOMapper.updateByPrimaryKeySelective(sessionDO);
        }
        if (cou <= 0) {
            logger.warn("session info add failed,cou=" + cou + ",userId=" + uid);
        }

        return sid;
    }

    @Override
    public void updateUserAttachInfoSelective(UserAttachDO userAttach) {
           userAttachDOMapper.updateSelective(userAttach);
    }

    private void setSession(String source, String sid, SessionDO sessionDO) {
        sessionDO.setSid(sid);
        sessionDO.setSource(source);
        sessionDO.setUpdateTime(new Date());
        Long expiredTime = LocalDateTime.now().plusDays(SESSION_EXPIRED_DATE).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        sessionDO.setExpireTime(String.valueOf(expiredTime));
    }



}
