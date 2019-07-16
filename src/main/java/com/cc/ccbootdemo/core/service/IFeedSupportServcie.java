package com.cc.ccbootdemo.core.service;

public interface IFeedSupportServcie {

    /**
     * 订阅feed
     * 用户输入命令 sub +feed的url
     *
     * @return
     */
     int   subFeed(String feedUrl);

    /**
     * 取消对feed的订阅
     * 用户输入命令 cancelSub +feed的url
     * @return
     */

     int   cancelSubFeed(String  feedUrl);

    /**
     * 获取feed信息  feedUrl
     * 不存在返回null
     *
     * @returnString
     */
     Object  getFeedInfo(String  feedUrl);


}
