package com.cc.ccbootdemo.web.aop;


import com.alibaba.fastjson.JSON;
import com.cc.ccbootdemo.facade.domain.common.exception.BizException;
import com.cc.ccbootdemo.facade.domain.common.exception.BusinessException;
import com.cc.ccbootdemo.facade.domain.common.exception.ParamException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 *
 * @desc basePackages 限定beforeBodyWrite只拦截处理 controller的方法
 * 不指定的话 异常情况下 exceptionHandler处理完 beforeBodyWrite还是会再处理一次
 * @author huacao
 * @since 2019-07-09
 */
@Slf4j
@ControllerAdvice(basePackages = "com.cc.ccbootdemo.web.controller")
@ResponseBody
public class ApiResponseAdvice  implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
       /* if(converterType.equals(MappingJackson2HttpMessageConverter.class)){
            return true;
        }
        return false;*/
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 连连回调返回的不包装
        if (request.getURI().getPath().contains("receiveNotify")) {
            return body;
        }
        dealJsonp(response, request);
        // 跨域处理
        crsfDeal(request, response);
        // 请求头处理
        setResponseHeaders(response);
        if(body instanceof ApiResponse){
            return body;
        }
        // 正常情况controller直接返回 原始的对象类型 在此处进行包装为PageAjaxResult 并json序列化

        return ApiResponse.success(body);
    }

    private void dealJsonp(ServerHttpResponse response, ServerHttpRequest request) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        String jsonpCallBack = servletRequest.getParameter("callback");
        if(StringUtils.isBlank(jsonpCallBack)){
            response.getHeaders().setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        }else{
            response.getHeaders().setContentType(MediaType.parseMediaType("application/javascript; charset=UTF-8"));

        }
    }

    private void setResponseHeaders(ServerHttpResponse response) {
        response.getHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.getHeaders().set("Access-Control-Max-Age", "3600");
        response.getHeaders().set("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.getHeaders().set("Access-Control-Allow-Credentials", "true");
    }

    private void crsfDeal(ServerHttpRequest request, ServerHttpResponse response) {
        String origin = String.valueOf(request.getHeaders().get("referer"));
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
/*
        if (validateReferfer(origin)) {
            log.info("validateCORSReferer pass,  orgin:" + origin);
            response.getHeaders().set("Access-Control-Allow-Origin", origin);
        }*/
    }


    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest request,  Exception ex) {
        ApiResponse apiResponse = ApiResponse.error();
        log.error("request exception ",ex);

        if (ex instanceof BizException) {
            return  dealBizExpection((BizException) ex, apiResponse);
        }else if (ex instanceof BindException) {
            return  dealBindExpection((BindException) ex, apiResponse);
        } else {
            return   dealUnExpectedException(ex, apiResponse);
        }
    }

    private Object dealBindExpection(BindException ex, ApiResponse apiResponse) {
        BindException exception = ex;
        apiResponse.setCode(Integer.valueOf(1001))
                .setMsg(exception.getMessage());
        return apiResponse;
    }

    private ApiResponse dealUnExpectedException(Exception ex, ApiResponse apiResponse) {
        apiResponse.setCode(9999);
        if (!StringUtils.isEmpty(ex.getMessage())) {
            apiResponse.setMsg(ex.getMessage());
        } else {
            apiResponse.setMsg("系统异常");
        }
        return apiResponse;
    }
 /**
    * describe:
    * 处理业务异常
    * @param
    * @author CAI.F
    * @date: 日期:2019/6/11 时间:11:36
    */
    private ApiResponse dealBizExpection(BizException ex, ApiResponse apiResponse) {
        BizException exception = ex;
        apiResponse.setCode(Integer.valueOf(exception.getErrorCode()))
                .setMsg(exception.getErrorMessage());
        if (exception.getData() != null) {
            apiResponse.setData(exception.getData());
        }
        return apiResponse;
    }


    /**
     * 校验referer
     * @param referer
     * @return
     */
    public static boolean validateReferfer(String referer) {

        if (StringUtils.isBlank(referer)) {
            return false;
        }

        try {
            URL url = new URL(referer);
            String host = url.getHost();

            return StringUtils.endsWith(host, "ccspace-test.com")
                    || StringUtils.endsWith(host, "ccspace.com")
                    || StringUtils.endsWith(host, ".ccspace.xin")
                    || StringUtils.endsWith(host, ".ccspace.info");
        } catch (MalformedURLException e) {
            log.error("url error,referer:" + referer, e);
        }

        return false;
    }
}