package com.cc.ccbootdemo.web.controller;

import com.cc.ccbootdemo.core.service.CityService;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.dataobject.City;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:34.
 */
@Controller
@RequestMapping(value="/cc")
public class CcTestController {
    private  City city=new City();
    @Resource
    CityService cityService;
    @Resource
    UserService userService;
    @ResponseBody
    @RequestMapping(value="/city/list")
    public List<City> getCityList(City city){
        return cityService.getCityList(city);
    }

    @ResponseBody
    @RequestMapping(value="/user/list")
    public String getUserList(Model model,User user){
        model.addAttribute("userList", userService.getUserList(user));

        return "userList";
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public String findAllCity(Model model) {
        List<City> cityList = cityService.getCityList(city);
        model.addAttribute("cityList", cityList);
        return "cityList";
    }

}
