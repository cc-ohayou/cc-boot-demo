package com.cc.ccbootdemo.web.controller;

import com.cc.ccbootdemo.core.service.CityService;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.bizobject.CustomProperties;
import com.cc.ccbootdemo.facade.domain.bizobject.Manga;
import com.cc.ccbootdemo.facade.domain.bizobject.UserInfo;
import com.cc.ccbootdemo.facade.domain.bizobject.param.*;
import com.cc.ccbootdemo.facade.domain.common.annotation.InterceptRequired;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.common.param.MQProducerParam;
import com.cc.ccbootdemo.facade.domain.common.param.ResetPwdParam;
import com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy.IOrderService;
import com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy.OrderDO;
import com.cc.ccbootdemo.facade.domain.common.util.PsPage;
import com.cc.ccbootdemo.facade.domain.dataobject.City;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import com.cc.ccbootdemo.facade.domain.dataobject.User;
import com.cc.ccbootdemo.web.holder.HeaderInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/22/022 16:34.
 */
@Controller
@RequestMapping(value="/{ver}/cc")
public class CcTestController extends BaseController{

    private  City defaultCity=new City();
    private Logger logger= LoggerFactory.getLogger(CcTestController.class);


    @Resource
    CityService cityService;
    @Resource
    UserService userService;
    @Resource
    IOrderService  orderService;

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
        List<UserInfo> list = userService.getUserList(user);
        model.addAttribute("userList", list);
        return USER_LIST_PATH_NAME;
    }

    @RequestMapping(value = "/get/userList")
    public List<UserInfo> getUser(User user) {
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
    public Object produceRQMsg(MQProducerParam param) {
           userService.produce(param);
        return "OK";
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/getDownloadUrl")
    public String getDownloadUrl() {
        return userService.getDownloadUrl();
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/getMangaList")
    public List<Manga> getMangaList(SearchBaseParam param) {
        return userService.getMangaList(param);
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/getCustomProperties")
    public CustomProperties getCustomProperties(SearchBaseParam param) {
        return userService.getCustomProperties(param);
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/getOperateList")
    public PsPage<OperateBiz> getOperateList(OperListQueryParam param) {
        return userService.getOperateList(param);
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/user/login")
    public UserInfo login(@Valid LoginParam param) {
        return userService.login(param);
    }

    @ResponseBody
    @RequestMapping(value = "/user/loginOut")
    public String loginOut() {
         userService.loginOut(HeaderInfoHolder.getUserId());
         return "ok";
    }


    @ResponseBody
    @RequestMapping(value = "/user/info")
    public UserInfo getUserInfoByUid(HeaderParam param) {

        return userService.getUserInfoByUid(HeaderInfoHolder.getUserId());
    }


    @ResponseBody
    @RequestMapping(value = "/update/userInfo")
    public String updateUserInfo(User param) {
        param.setUid(HeaderInfoHolder.getUserId());
        userService.updateUserInfo(param);
        return "OK";
    }

    /**
     * 修改头像
     */
    @ResponseBody
    @RequestMapping(value = "/modify/headImg")
    public String updateHeadImg(@RequestParam("file") MultipartFile file) throws Exception {
        return userService.updateHeadImg(HeaderInfoHolder.getUserId(), file);
    }

    /**
     * @description 更新首页背景图
     * @author CF create on 2019/1/16 16:08
     */
    @ResponseBody
    @RequestMapping(value = "/modify/bgImg")
    public String modifyBgImg(@RequestParam("file") MultipartFile file) throws Exception {
        return userService.modifyBgImg(HeaderInfoHolder.getUserId(), file);
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/register")
    public String register(RegistParam param) throws Exception {
         userService.regist(param);
         return "OK";
    }

    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/forget/pwd")
    public String forgetPwd(String mail) throws Exception {
        userService.forgetPwd(mail);
        return "OK";
    }
    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/reset/pwd")
    public String resetPwd(ResetPwdParam resetParam) throws Exception {
        userService.resetPwd(resetParam);
        return "OK";
    }


    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/send/email")
    public String sendEmail(String email,HttpServletResponse response) throws Exception {
        userService.sendEmail(email);
       return "success";

    }


    @InterceptRequired(required = false)
    @ResponseBody
    @RequestMapping(value = "/order/handle")
    public String handleOrderDynamicTest(OrderDO orderDO) throws Exception {
        return orderService.handleOrder(orderDO);


    }

}
