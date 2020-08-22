package com.qunhe.toilet.facade.domain.common.util.wx.work;
import com.google.common.collect.Lists;


import com.alibaba.fastjson.JSONObject;
import com.qunhe.toilet.facade.domain.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.qunhe.toilet.facade.domain.common.util.wx.work.WxWorkConstant.API_HOTS;
import static com.qunhe.toilet.facade.domain.common.util.wx.work.WxWorkConstant.URL_TEXT;


/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
@Slf4j
public class WxWorkMsgClient {

    private static ThreadPoolExecutor messageExecutor = new ThreadPoolExecutor(10, 30,
            60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    /**
     * 发送文本消息
     * @param wxWorkBody
     */
    public static void sendTextMessage(WxWorkBody wxWorkBody) {
        sendMessage(URL_TEXT, wxWorkBody);
    }

    /**
     * 发送企信文本消息
     * @param wxWorkBody
     * @param isAsync
     */
    public static void sendTextMessage(WxWorkBody wxWorkBody, boolean isAsync) {
        if (isAsync) {
            messageExecutor.submit(() -> sendMessage(URL_TEXT, wxWorkBody));
        } else {
            sendMessage(URL_TEXT, wxWorkBody);
        }
    }


    /**
     * 异步发送文本通知
     * 需要开启@EnableAsync
     * @param wxWorkBody
     */
    @Async
    public static void asyncSendTextMessage(WxWorkBody wxWorkBody) {
        sendMessage(URL_TEXT, wxWorkBody);
    }



    /**
     * 发送卡片消息
     * @param wxWorkBody
     */
    public static void sendCardMessage(WxWorkBody wxWorkBody) {
        sendMessage(WxWorkConstant.URL_CARD, wxWorkBody);
    }



    /**
     * 发送卡片消息
     * @param wxWorkBody
     * @param isAsync
     */
    public static void sendCardMessage(WxWorkBody wxWorkBody, boolean isAsync) {
        if (isAsync) {
            messageExecutor.submit(() -> sendMessage(WxWorkConstant.URL_CARD, wxWorkBody));
        } else {
            sendMessage(WxWorkConstant.URL_CARD, wxWorkBody);
        }
    }



    /**
     * 异步发送卡片消息
     * 需要开启@EnableAsync
     * @param wxWorkBody
     */
    @Async
    public static void asyncSendCardMessage(WxWorkBody wxWorkBody) {
        sendMessage(WxWorkConstant.URL_CARD, wxWorkBody);
    }

    /**
     * 发送企信通知
     * @param agentId
     * @param content
     * @param toUser
     * @param toParty
     * @param toTag
     */
    public static void sendMessage(String agentId, String content, List<String> toUser,
                            List<String> toParty, List<String> toTag, String title, String description, String url,
                            String buttonText, MessageType messageType) {
        try {
            String requestUrl = null;
            WxWorkBody wxWorkBody = null;
            switch (messageType) {
                case TEXT:
                    wxWorkBody = new WxWorkTextBody().setAgentId(agentId)
                            .setToParty(toParty).setToTag(toTag).setToUser(toUser);
                    ((WxWorkTextBody) wxWorkBody).setContent(content);
                    requestUrl = URL_TEXT;
                    break;
                case CARD:
                    wxWorkBody = new WxWorkCardBody().setAgentId(agentId)
                            .setToParty(toParty).setToTag(toTag).setToUser(toUser);
                    ((WxWorkCardBody) wxWorkBody).setTitle(title).setUrl(url)
                            .setDescription(description).setButtonText(buttonText);
                    requestUrl = WxWorkConstant.URL_CARD;
                    break;

                default:

            }
            if (null == requestUrl) {
                log.warn("WxWorkClient - sendTextMessage failed : unsupport message type.");
                return;
            }
            sendMessage(requestUrl, wxWorkBody);
        } catch (Exception e) {
            log.error("WxWorkClient - sendTextMessage error : {}", e.getMessage(), e);
        }
    }

    /**
     * 发送消息
     * @param requestUrl
     * @param wxWorkBody
     */
    public static void sendMessage(String requestUrl, WxWorkBody wxWorkBody) {
        if (null == wxWorkBody.getAgentId()) {
            wxWorkBody.setAgentId(WxWorkConstant.ENTERPRISE_ASSISTANT_AGENTID);
        }
        HttpPost httpPost = new HttpPost(requestUrl);
        StringEntity entity = new StringEntity(JSONObject.toJSONString(wxWorkBody), ContentType
                .APPLICATION_JSON);
        httpPost.setEntity(entity);
        String stringResult = HttpClientUtil.sendApplicationJsonPost(httpPost, null);
        final Result result = JSONObject.parseObject(stringResult, Result.class);
        if (null != result && !result.success()) {
            log.error("WxWorkClient - sendTextMessage failed : {}", result.getM());
        }
    }

    public static void main(String[] args) {
        WxWorkTextBody wxWorkBody = new WxWorkTextBody();
        wxWorkBody.setToUser(Arrays.asList("bupo"));
//        wxWorkBody.setToParty();
//        wxWorkBody.setToTag(Lists.newArrayList());
        wxWorkBody.setAgentId("0");
        wxWorkBody.setContent("bupo toilet test");
        sendMessage(URL_TEXT,wxWorkBody);
    }

}
