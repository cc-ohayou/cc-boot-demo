package com.cc.ccbootdemo.web.controller;


import com.cc.ccbootdemo.core.service.RedisService;
import com.cc.ccbootdemo.facade.domain.bizobject.param.RedisSearchParam;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.cc.ccbootdemo.facade.domain.dataobject.RedisKeyDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/2 9:59.
 */
@Controller
@RequestMapping("/{ver}/redis")
public class RedisController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    RedisService redisService;

    @ResponseBody
    @RequestMapping(value = "/keyList", method = {RequestMethod.GET})
    public Object keyList(RedisSearchParam param) throws ParamException {

        return redisService.getKeyList(param);
    }
//    @ResponseBody
    @RequestMapping(value = "")
    public Object index() throws ParamException {
        return "redis";
    }
    @ResponseBody
    @RequestMapping(value = "/key/biztype", method = {RequestMethod.GET})
    public Object keyBizTypeList(RedisSearchParam param) {

        return redisService.getKeyBizTypeList(param);
    }

    @ResponseBody
    @RequestMapping(value = "/add/key")
    public String addKey(RedisKeyDO param) throws ParamException {
        redisService.addKey(param);
        return "OK";
    }
    @ResponseBody
    @RequestMapping(value = "/key/detail")
    public Object getKeyDetail(RedisSearchParam param) throws ParamException {
        return redisService.getKeyDetail(param);
    }

}
