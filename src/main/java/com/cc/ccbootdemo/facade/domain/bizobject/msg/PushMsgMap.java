package com.cc.ccbootdemo.facade.domain.bizobject.msg;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 19:04.
 */
@Data
public class PushMsgMap {

    private String title;
    private String content;
    private String type;
    private String merchantId;
    private String targetUserId;

}
