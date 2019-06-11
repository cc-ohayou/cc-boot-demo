package com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy;

import org.springframework.stereotype.Component;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:48.
 */
@Component
@HandleType(value="3")
public class PromotionHandler  extends AbstractHandler<OrderDO> {

    @Override
    public String handle(OrderDO orderDO) {
        return "handle promotion order";
    }
}