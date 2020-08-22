package com.qunhe.toilet.facade.domain.common.condition;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author bupo
 * @DATE 2020/8/21 16:21
 * @Description
 */
@Data
@Accessors(chain=true)
public class ToiletPitSubRecordCondition {


    /**
     * 预约起始时间
     */
    private Date notifyStartTime;

    /**
     * 预约时填入的截止时间
     */
    private Date notifyEndTimeGt;

    private Date notifyEndTimeLt;

    /**
     * 预约的楼层id
     */
    private Integer floorId;

    /**
     * 0   1 已作废
     */
    private Boolean  deleted;

    /**
     * 预约用户id
     */
    private Long uid;



}
