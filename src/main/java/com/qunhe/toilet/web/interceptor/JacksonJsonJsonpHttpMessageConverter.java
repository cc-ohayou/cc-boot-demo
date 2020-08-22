package com.qunhe.toilet.web.interceptor;


import com.qunhe.toilet.facade.domain.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author huacao$
 * @since 2019/7/16$ 10:52 PM$
 */
public class JacksonJsonJsonpHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    protected String[] jsonpParameterNames = new String[]{"callback","jsonp"};



    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String text = JsonUtil.write(obj);
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
        outputMessage.getBody().write(text.getBytes());
    }


    public void setJsonpParameterNames(String[] jsonpParameterNames) {
        this.jsonpParameterNames = jsonpParameterNames;
    }


}