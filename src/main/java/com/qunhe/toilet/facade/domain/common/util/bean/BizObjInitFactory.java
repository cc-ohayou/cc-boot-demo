package com.qunhe.toilet.facade.domain.common.util.bean;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;

/**
 * @AUTHOR ZFJ
 * @DATE Created on 2018/12/7
 */

@Slf4j
public class BizObjInitFactory {


    private BizObjInitFactory() { }

    /**
     * 此方法用作初始化值操作.利用内省获取方法时,必须给出<get><set>方法
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T initValue(Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            valueSet(obj);
            return obj;
        } catch (Exception e) {
            log.info("BizObjInitFactory method initValue transform fail exception:" + e);
        }
        return null;
    }

    /**
     * 此方法用作清空类的值.利用内省获取方法时,必须给出<get><set>方法
     *
     * @param obj
     */
    public static <T> void clearValue(T obj) {
        try {
            valueSet(obj);
        } catch (Exception e) {
            log.info("BizObjInitFactory method clearValue transform fail exception:" + e);
        }
    }

    private static <T> void valueSet(T obj) throws Exception {
        BeanInfo beaninfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beaninfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            if (BigDecimal.class.equals(property.getPropertyType())) {
                property.getWriteMethod().invoke(obj, BigDecimal.ZERO);
            }
            if (Long.class.equals(property.getPropertyType())) {
                property.getWriteMethod().invoke(obj, 0L);
            }
        }
    }


    public static void main(String[] args) {

    }
}
