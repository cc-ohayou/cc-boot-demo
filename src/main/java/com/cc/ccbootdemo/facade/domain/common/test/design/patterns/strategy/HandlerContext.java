package com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy;

import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.common.util.ClassScaner;
import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:21.
 */
public   class HandlerContext {

    private  Map handlerMap=new ConcurrentHashMap<>();
    private  Map<String,Class> strategyClazzMap;

    public HandlerContext(Map<String, Class> strategyClazzMap) {
        this.strategyClazzMap=strategyClazzMap;
    }


    public <T> T getHandlerByType(String type){

       if(handlerMap.get(type)!=null){
           return (T)handlerMap.get(type);
       }
       if(strategyClazzMap.isEmpty()){
           throw new BusinessException("HandlerContext strategyClazzMap init error  cannot be empty ");
       }
       Class<T>  clazz=strategyClazzMap.get(type);
       if(clazz==null){
           throw  new ParamException("not found handler for type:"+type);
       }
       T t = getInstanceByClazzType(clazz);

       handlerMap.put(type,t);
       return t;
   }

    private <T> T getInstanceByClazzType(Class<T> clazz) {
        T t = null;
        try {
            t=clazz.newInstance();
         } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
       }
        return t;
    }

    private static final String HANDLER_PACKAGE="com.cc.ccbootdemo.facade.domain.common.test.design";

    public static void main(String[] args) {


        Map<String,Class> handlerMap   = Maps.newHashMapWithExpectedSize(3);
        ClassScaner.scan(HANDLER_PACKAGE,HandleType.class).forEach(clazz -> {


            String type =((HandleType)clazz.getAnnotation(HandleType.class)).value();
            handlerMap.put(type,clazz);
        });
        HandlerContext context=new HandlerContext(handlerMap);

        OrderDO orderDO=new OrderDO();
        orderDO.setCode("1001");
        orderDO.setType("1");
        orderDO.setPrice(BigDecimal.ONE);
        testByType(context, orderDO, "1");
        testByType(context, orderDO, "2");
        testByType(context, orderDO, "3");
    }

    private static void testByType(HandlerContext context, OrderDO orderDO, String type) {
        AbstractHandler handler = context.getHandlerByType(type);
        orderDO.setType(type);
        System.out.println(handler.handle(orderDO));
    }
}
