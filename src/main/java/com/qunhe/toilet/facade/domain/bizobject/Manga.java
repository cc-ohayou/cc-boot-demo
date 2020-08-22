package com.qunhe.toilet.facade.domain.bizobject;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/28 16:44.
 */
@Data
public class Manga {

    /**
     * area : 中国大陆
     * arealimit : 316
     * attention : 137743
     * bangumi_id : 0
     * bgmcount : 21
     * cover : http://i0.hdslb.com/bfs/bangumi/ac7a5aa48472786d9f16a248590a610f3319ef7a.jpg
     * danmaku_count : 161249
     * ep_id : 259665
     * favorites : 137743
     * is_finish : 0
     * lastupdate : 1545972000
     * lastupdate_at : 2018-12-28 12:40:00
     * new : true
     * play_count : 6929133
     * pub_time :
     * season_id : 23695    ss23695    https://www.bilibili.com/bangumi/play/ss23695 进入播放页面
     * season_status : 2
     * spid : 0
     * square_cover : http://i0.hdslb.com/bfs/bangumi/0c85a95abe82e3af8b4659a6cf4584c0ea27cfec.jpg
     * title : 峡谷重案组
     * viewRank : 0
     * weekday : 5
     */

//    //区域限制
//    private int arealimit;
//    private int attention;
//    private int bangumi_id;
//
//
//    //弹幕数
//    private int danmaku_count;
//    private int ep_id;
//    //追番数
//    private int favorites;
//    private int is_finish;
//    //    更新时间秒数
//    private int lastupdate;
//    //    更新时间
//    private String lastupdate_at;
//
//    //    播放数
//    private int play_count;
//    private String pub_time;
//    private int season_id;
//    private int season_status;
//    private int spid;
    /**
     * 地区 中国大陆 china
     */
    private String area;
    //    新番与否
    private boolean isNew;
    //    封面  square_cover替换为 coverImage 方形封面图
    private String coverImage;
    //    封面图 点击新番后进入的作品图片
    private String cover;
    //    标题 名称
    private String title;
    //    播出日期
    private int weekday;
    //更新至第几话 bgmcount替换为   nowEpisode
    private String nowEpisode;
    //排名
    private int viewRank;


}
