package com.cc.ccbootdemo.web.controller;

import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.util.RandomStringUtil;
import com.cc.ccbootdemo.web.aop.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/14/014 18:31.
 */
@RestController
@RequestMapping(value = "/hello",produces = "application/json;charset=utf-8")
public class HelloController {

    @RequestMapping(value = "/say")
    public String index() {
        return "Hello World cc";
    }
    /**
     *
     * @param 
     * @return 
     * @since 2019/7/16 2:46 PM
     * @author huacao
     */
    @RequestMapping(value = "/jsonp",method = RequestMethod.GET)
    public Map jsonpCall(String name) {
        Map map=new HashMap();
        map.put("jsonpName",name);
        map.put("code",RandomStringUtil.generateString(6));

        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/test2")
    public ApiResponse test2() {

        return ApiResponse.success("already packaged result return test");
    }

    @ResponseBody
    @RequestMapping(value = "/test3")
    public String  test3() {

        return "normal String return test";
    }



    @ResponseBody
    @RequestMapping(value = "/test1")
    public String  test1() {
        int a=0;
        int b=100;
        int c=b/a;
        return "test3";
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    public String  test() {
        int a=0;
        int b=100;
       if(b>a){
           throw new BusinessException("test bizException");
       }
        return "test3";
    }



}