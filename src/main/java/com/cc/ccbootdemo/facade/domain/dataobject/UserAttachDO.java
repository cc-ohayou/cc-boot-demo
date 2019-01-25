package com.cc.ccbootdemo.facade.domain.dataobject;

import lombok.Data;

import javax.persistence.Table;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/16 16:41.
 */
@Data
@Table(name = "user_attach")
public class UserAttachDO {
    private String uid;
    private String mainBgUrl;
    private String headLayBgImgUrl;

    private String roleCodes;

    private String createTime;
    private String updateTime;
}
