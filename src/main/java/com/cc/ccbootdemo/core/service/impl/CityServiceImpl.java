package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.manager.CityManager;
import com.cc.ccbootdemo.core.mapper.CityMapper;
import com.cc.ccbootdemo.core.service.CityService;
import com.cc.ccbootdemo.facade.domain.dataobject.City;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:38.
 */
@Service
public class CityServiceImpl implements CityService {

    @Resource
    CityManager cityManager;
    @Override
    public List<City> getCityList(City params) {
        return cityManager.getCityList(params.getCityName());
    }
}
