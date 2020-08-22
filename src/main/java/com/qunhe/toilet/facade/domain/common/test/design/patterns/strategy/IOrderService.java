package com.qunhe.toilet.facade.domain.common.test.design.patterns.strategy;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:15.
 */
public interface IOrderService {


    /**
     * describe:
     * 根据订单类型做出不同的处理
     * @param
     * @author CAI.F
     * @date: 日期:2019/6/11 时间:11:16
     */
      String  handleOrder(OrderDO orderDO);
}
