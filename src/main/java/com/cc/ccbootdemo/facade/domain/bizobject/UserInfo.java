package com.cc.ccbootdemo.facade.domain.bizobject;

import com.cc.ccbootdemo.facade.domain.dataobject.UserAttachDO;
import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/14 10:24.
 */
@Data
public class UserInfo extends UserAttachDO{
    /**
     * 昵称 可允许特殊符号 但要防止sql注入 可以允许表情
     */
    private String uid;
    private String userName;
    private String pwd;
    private String nickName;
    private String description;
    private String phone;
    /**
     * 邮箱不可为空  用于找回密码
     */
    private String mail;
    private String headImage;
    private String roleCode;
    private String createTime;
    private String updateTime;
    private String sid;
    private String salty;
}
