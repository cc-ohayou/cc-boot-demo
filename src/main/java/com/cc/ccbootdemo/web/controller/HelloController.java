package com.cc.ccbootdemo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/14/014 18:31.
 */
@RestController
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/hello",  produces = "application/json;charset=utf-8")
    public String index() {
        return "Hello World cc";
    }

}