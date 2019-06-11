package com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:34.
 */
@Service
public class OrderServiceImpl implements IOrderService{

    @Resource
    HandlerContext handlerContext;

    @Override
    public String handleOrder(OrderDO orderDO) {
        AbstractHandler handler=handlerContext.getHandlerByType(orderDO.getType());
        return handler.handle(orderDO);
    }
}
