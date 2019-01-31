package com.cc.ccbootdemo.web.controller;

import com.cc.ccbootdemo.core.service.SupportService;
import com.cc.ccbootdemo.facade.domain.common.annotation.InterceptRequired;
import com.cc.ccbootdemo.facade.domain.common.param.GateWayReqParam;
import com.cc.ccbootdemo.web.holder.HeaderInfoHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/8 10:10.
 */
@RestController
@RequestMapping("/{ver}/support/")
public class SupportController {


    @Resource
    SupportService supportService;

    @RequestMapping("gateWay/req")
    @InterceptRequired(required = false)
    public  String gateWayReq(GateWayReqParam param ){
        param.setUserId(HeaderInfoHolder.getUserId());
        supportService.gateWayReq(param);
        return "OK";
    }

    @RequestMapping("test")
    @InterceptRequired(required = false)
    public  String testReq(){
        try {
            return supportService.test();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @ResponseBody
    @InterceptRequired(required = false)
    @RequestMapping(value = "/uploadImg")
    public String uploadImg(@RequestParam("file") MultipartFile file,String key,String pwd) throws Exception {

        return supportService.uploadImg(file,key,pwd);
    }



}
