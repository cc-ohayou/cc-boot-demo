package com.cc.ccbootdemo.facade.domain.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 15:16.
 */
@Data
@Accessors(chain=true)
public class City {

    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;
}
