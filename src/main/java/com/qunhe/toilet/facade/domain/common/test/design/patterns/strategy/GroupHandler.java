package com.qunhe.toilet.facade.domain.common.test.design.patterns.strategy;

import org.springframework.stereotype.Component;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:47.
 */
@Component
@HandleType(value="2")
public class GroupHandler  extends AbstractHandler<OrderDO> {


    @Override
    public String handle(OrderDO orderDO) {
        return "handle group order";
    }
}