package com.qunhe.toilet.facade.domain.common.util.wx.work;


/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
public class WxWorkConstant {

    /**
     * agent
     */
    public static final String ENTERPRISE_ASSISTANT_AGENTID = "0";

    /**
     * 服务域名
     */
    public static final String API_HOTS = "http://message-center.qunhequnhe.com";

    /**
     * 发送文本消息
     */
    public static final String URL_TEXT = API_HOTS + "/message-center/api/wxwork/text";

    /**
     * 发送卡片消息
     */
    public static final String URL_CARD = API_HOTS + "/message-center/api/wxwork/textcard";

    /**
     * 发送透传消息
     */
    public static final String URL_PASS_THROUGH = API_HOTS + "/message-center/api/wxwork/passthrough";

}
