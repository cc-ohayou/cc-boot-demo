package com.qunhe.toilet.web.controller;

import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.param.ResetPwdParam;
import com.qunhe.toilet.facade.domain.common.param.TolietParam;
import com.qunhe.toilet.facade.domain.common.util.JsonUtil;
import com.qunhe.toilet.facade.domain.common.util.RandomStringUtil;
import com.qunhe.toilet.web.aop.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/14/014 18:31.
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @RequestMapping(value = "/say")
    public String index(@RequestBody(required=false) TolietParam param) {
        String  reqStr=param!=null? JsonUtil.write(param):"";
        return "Hello your param is"+reqStr;
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
    @RequestMapping(value = "/test2")
    public ApiResponse test2() {

        return ApiResponse.success("already packaged result return test");
    }

    @RequestMapping(value = "/test3")
    public String  test3(@RequestBody(required = false) ResetPwdParam param,@RequestParam(value="verifyCode",required = false) String verifyCode) {

        return "normal String return test";
    }



    @RequestMapping(value = "/test1")
    public String  test1() {
        int a=0;
        int b=100;
        int c=b/a;
        return "test3";
    }

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