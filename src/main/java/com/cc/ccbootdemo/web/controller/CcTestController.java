package com.cc.ccbootdemo.web.controller;

import com.cc.ccbootdemo.core.service.CityService;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.dataobject.City;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:34.
 */
@Controller
@RequestMapping(value="/cc")
public class CcTestController extends BaseController{

    private  City defaultCity=new City();
    private Logger logger= LoggerFactory.getLogger(CcTestController.class);


    @Resource
    CityService cityService;
    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping(value="/city/list")
    public List<City> getCityList(City city){
        return cityService.getCityList(city);
    }

    @RequestMapping(value="/user")
    public String getUserList(ModelMap model, User user){
        model.addAttribute("userList", userService.getUserList(user));
        //返回的userList指向对应名称的freemaker文件 若果不加responseBody注解
        //直接返回字符串找不到对应的ftl文件就会报错 使用@ResponseBody则会正常返回字符串
        return USER_LIST_PATH_NAME;
    }

    @RequestMapping(value = "/api/city")
    public String findAllCity(Model model) {
        List<City> cityList = cityService.getCityList(defaultCity);
        model.addAttribute("cityList", cityList);
        return "cityList";
    }
    @RequestMapping(value = "/add/city")
    public String addCity(Model model,City city) {
        logger.info("city is "+city);
        if(city!=null){
                cityService.addCity(city);
        }
        city=defaultCity;
        List<City> cityList = cityService.getCityList(city);
        model.addAttribute("cityList", cityList);
        return "cityList";
    }
    @RequestMapping(value = "/add/user")
    public String addUser(ModelMap model,User user) {
        logger.info("user is "+user);
        if(user!=null) {
            userService.addUser(user);
        }
        List<User> list = userService.getUserList(user);
        model.addAttribute("userList", list);
        return USER_LIST_PATH_NAME;
    }

    @RequestMapping(value = "/get/userList")
    public List<User> getUser(User user) {
        return userService.getUserList(user);
    }

    @RequestMapping(value = "/exce/test")
    public Object exception(User user) {
        User u=null;
        throw new ParamException("test custom exception ");
//        return u.getCity();
    }

    @ResponseBody
    @RequestMapping(value = "/rocketmq/produce")
    public Object exception(MQProducerParam param) {
           userService.produce(param);
        return "OK";
    }
}
