package com.qunhe.toilet.facade.domain.common.util.wx.work;


/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
public enum MessageType {

    TEXT(1, "文本消息"),
    CARD(2, "卡片消息"),
    PASS_THROUGH(3, "透传消息");

    private int code;

    private String desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
