package com.qunhe.toilet.core.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 17:52.
 */
public interface BaseService {

    default boolean  isBlank(String str){
        return  StringUtils.isBlank(str);
    }

    default boolean  nonNull(Object  obj){
        return Objects.nonNull(obj);
    }
    default boolean isNotBlank(String str){
        return  StringUtils.isNotBlank(str);
    }

    default boolean isEmpty(Collection collection){
        return CollectionUtils.isEmpty(collection);
    }

    default boolean isNotEmpty(Collection collection){
        return  CollectionUtils.isNotEmpty(collection);
    }
}
