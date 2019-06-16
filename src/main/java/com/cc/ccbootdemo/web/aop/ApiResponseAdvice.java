package com.cc.ccbootdemo.web.aop;


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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@ControllerAdvice(basePackages = "com.cc.ccbootdemo.web.controller")
@ResponseBody
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {


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
        // 连连回调返回的不在包装
        if (request.getURI().getPath().contains("receiveNotify")) {
            return body;
        }
        //先做版本判断，然后加密处理
        ApiResponse succ = ApiResponse.success().setData(body);
        return succ;
    }


    @ExceptionHandler(value = Exception.class)
    public ApiResponse exceptionHandler(HttpServletRequest request,  Exception ex) {
        ApiResponse apiResponse = ApiResponse.error();
        log.error("request exception ",ex);
        if (ex instanceof BizException) {
            return dealBizExpection((BizException) ex, apiResponse);
        } else {
            return dealUnExpectedException(ex, apiResponse);
        }

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
}