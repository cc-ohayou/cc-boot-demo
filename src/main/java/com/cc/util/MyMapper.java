package com.cc.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/1/001 15:15.
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T>{
}
