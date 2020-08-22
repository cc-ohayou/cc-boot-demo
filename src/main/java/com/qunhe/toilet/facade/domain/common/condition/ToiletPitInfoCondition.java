package com.qunhe.toilet.facade.domain.common.condition;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author bupo
 * @DATE 2020/8/21 14:50
 * @Description
 */
@Data
@Accessors(chain=true)
public class ToiletPitInfoCondition {


    private Long buildNo;

    private Integer floorNo;
    /**
     * 几号卫生间 一层可能有多个
     */
    private Integer roomNo;

    private Integer pitNo;
    /**
     *   0 空闲   1 使用中
     */
    private Integer usingState;
    /**
     * man  woman  both
     */
    private Integer sexType;

    /**
     * 1 无效 0有效
     */
    private Boolean deleted;

}
