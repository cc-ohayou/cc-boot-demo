package com.cc.ccbootdemo.core.manager;

import com.cc.ccbootdemo.facade.domain.dataobject.City;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:41.
 */
public interface CityManager {
    List<City> getCityList(String params);
}
