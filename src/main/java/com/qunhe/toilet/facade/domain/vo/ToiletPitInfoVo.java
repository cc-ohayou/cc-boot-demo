package com.qunhe.toilet.facade.domain.vo;

import lombok.Data;
/**
 * @Author bupo
 * @DATE 2020/8/21 18:01
 * @Description
 */
@Data
public  class ToiletPitInfoVo {
    /**
     * buildingName : 1号楼
     * floorNo : 8
     * roomNo : 1
     * roomName : 听雨阁 1号
     * pitNo : 1
     * usingState : true
     * usedTime : 10
     * sexType : man
     * availableSign : true
     * inconvenientSign : false
     */

    private String buildingName;
    private long buildNo;
    private int floorNo;
    private int roomNo;
    private String roomName;
    private int pitNo;
    private boolean usingState;
    /**
     * 单位 分
     */
    private int usedTime;
    private String sexType;
    private boolean availableSign;
    private boolean inconvenientSign;


}