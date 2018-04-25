package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.CityManager;
import com.cc.ccbootdemo.core.mapper.cluster.CityMapper;
import com.cc.ccbootdemo.facade.domain.dataobject.City;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:41.
 */
@Component
public class CityManagerImpl implements CityManager {
    @Resource
    CityMapper cityMapper;
    @Override
    public List<City> getCityList(String name) {
        if(StringUtils.isEmpty(name)){
            return cityMapper.getAllCityList();
        }
        return cityMapper.findByName(name);
    }
}
