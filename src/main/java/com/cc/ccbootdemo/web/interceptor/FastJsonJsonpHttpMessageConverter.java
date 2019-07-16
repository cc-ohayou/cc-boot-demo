package com.cc.ccbootdemo.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author huacao$
 * @since 2019/7/16$ 10:52 PM$
 */

public class FastJsonJsonpHttpMessageConverter extends FastJsonHttpMessageConverter {
    protected String[] jsonpParameterNames = new String[]{"callback","jsonp"};

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String text = JSON.toJSONString(obj, getFeatures());
        String callback = null;
        for (int i = 0; i< jsonpParameterNames.length; i++){
            callback = request.getParameter(jsonpParameterNames[i]);
            if(callback != null) {
                break;
            }
        }
        if(StringUtils.isNotBlank(callback)){
            text = new StringBuilder(callback).append("(").append(text).append(")").toString();
        }
        outputMessage.getBody().write(text.getBytes(getCharset()));
    }

    public void setJsonpParameterNames(String[] jsonpParameterNames) {
        this.jsonpParameterNames = jsonpParameterNames;
    }
}