package com.cc.ccbootdemo.core.service;

import com.cc.ccbootdemo.facade.domain.dataobject.City;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:37.
 */
public interface CityService {

    List<City> getCityList(City params);
}
