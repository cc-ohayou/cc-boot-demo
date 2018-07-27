package com.cc.ccbootdemo.facade.domain.common.util.push;

import com.cc.ccbootdemo.facade.domain.bizobject.msg.PushMsgMap;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 18:40.
 */
@Data
public class PushReq {
    private int code;
    private PushMsgMap msgMap;//消息内容装载体
    private List<String> targetUserIds = new ArrayList<>();//发送目标用户的用户ID


}
