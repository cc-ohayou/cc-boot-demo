package com.qunhe.toilet.core.manager;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/16 18:34.
 */
public interface BaseManager {




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
