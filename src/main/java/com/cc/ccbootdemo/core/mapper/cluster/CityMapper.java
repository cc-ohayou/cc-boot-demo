package com.cc.ccbootdemo.core.mapper.cluster;

import com.cc.ccbootdemo.facade.domain.dataobject.City;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 15:19.
 */
//@Mapper
public interface CityMapper extends Mapper<City> {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    List<City> findByName(@Param("cityName") String cityName);

    List<City> getAllCityList();
}
