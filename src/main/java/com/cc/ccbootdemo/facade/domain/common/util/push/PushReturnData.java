package com.cc.ccbootdemo.facade.domain.common.util.push;

/**
* @AUTHOR CF
* @DATE Created on 2018/7/10 18:40.
*/
public class PushReturnData {
   private String msgType;
   private String title;
   private String msg;
   private String content;
   private String strategyId;


   public String getMsgType() {
       return msgType;
   }

   public void setMsgType(String msgType) {
       this.msgType = msgType;
   }

   public String getTitle() {
       return title;
   }

   public void setTitle(String title) {
       this.title = title;
   }

   public String getMsg() {
       return msg;
   }

   public void setMsg(String msg) {
       this.msg = msg;
   }

   public String getContent() {
       return content;
   }

   public void setContent(String content) {
       this.content = content;
   }

   public String getStrategyId() {
       return strategyId;
   }

   public void setStrategyId(String strategyId) {
       this.strategyId = strategyId;
   }
}
