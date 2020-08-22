package com.qunhe.toilet.facade.domain.common.test.design.patterns.strategy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:10.
 */
@Data
public class OrderDO{
    private String code;
    private BigDecimal price;
    /**
     * 订单类型
     */
    private String type;


}
