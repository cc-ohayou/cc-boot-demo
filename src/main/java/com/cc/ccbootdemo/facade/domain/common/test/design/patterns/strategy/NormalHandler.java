package com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy;

import org.springframework.stereotype.Component;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:46.
 */
@Component
@HandleType(value="1")
public class NormalHandler  extends AbstractHandler<OrderDO> {



    @Override
    public String handle(OrderDO orderDO) {
        return "handle normal order";
    }
}
