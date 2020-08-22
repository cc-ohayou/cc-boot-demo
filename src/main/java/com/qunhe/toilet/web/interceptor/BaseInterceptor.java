package com.qunhe.toilet.web.interceptor;

import com.qunhe.toilet.facade.domain.common.util.BeanUtil;
import com.qunhe.toilet.web.holder.HeaderInfo;
import com.qunhe.toilet.web.holder.HeaderInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/8/7 11:14.
 */
@Slf4j
public abstract class BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        setHeaderHolderInfo(request);
        return isCrosRequest(request, response);
    }

    private void setHeaderHolderInfo(HttpServletRequest request) {
        Map headerInfoMap = new HashMap();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headerInfoMap.put(key, value);
        }
//        System.err.println(Thread.currentThread().getName());
//        headerInfoMap.put("ip", HttpUtil.getIpAddress(request));

        HeaderInfo info=new HeaderInfo();
        try {
            info= BeanUtil.mapToObject(headerInfoMap,HeaderInfo.class);
            HeaderInfoHolder.setHeaderInfo(info);
        } catch (Exception e) {
            log.error("Header map transfer to bean error!",e);
        }
    }
    /**
     * @description  解决跨域请求的问题
     * 跨域请求科普博客
     * http://www.ruanyifeng.com/blog/2016/04/cors.html
     *
     * @author CF create on 2018/8/7 15:09
     */
    private boolean isCrosRequest(HttpServletRequest request, HttpServletResponse response) {
        boolean flag=false;
        //如果是预检请求 设置下allow的header参数 OPTIONS为预检请求
        if(request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())){
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            //鉴于header中存放了很多业务相关的信息 直接用*匹配
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Request-Headers", "*");
            //30min
            response.setHeader("Access-Control-Max-Age", "3600");
            flag=true;
        }else{
            //   允许跨域，允许复杂请求  也可以使用,号隔开的n个域名指定死某些域名允许访问
//            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        return flag;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
