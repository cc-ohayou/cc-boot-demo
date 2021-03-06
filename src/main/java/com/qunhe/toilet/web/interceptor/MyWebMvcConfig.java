package com.qunhe.toilet.web.interceptor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/1/001 18:22.
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getExceptionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public SessionInterceptor getSessionInterceptor(){
        return new SessionInterceptor();
    }

     /**
     * 配置自定义的HttpMessageConverter
     *注：
     *1.configureMessageConverters:
      * 重载会覆盖掉spring mvc默认注册的 多个HttpMessageConverter。
     *2.extendsMessageConverter：仅添加一个自定义 的HttpMessageConverter，
      * 不覆盖默认注册 的HttpMessageConverter.
      *     使用extendsMessageConverter 添加一个自定义的HttpMessageConverter
     **/
    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverter(){
        //创建FastJson信息转换对象
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonJsonpHttpMessageConverter();

        //创建Fastjosn对象并设定序列化规则
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 中文乱码解决方案
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
        mediaTypes.add(MediaType.MULTIPART_FORM_DATA);
        mediaTypes.add(MediaType.TEXT_HTML);
//        mediaTypes.add(MediaType.MULTIPART_FORM_DATA_VALUE);//设定json格式且编码为UTF-8
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);

        //规则赋予转换对象
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        return fastJsonHttpMessageConverter;

    }



    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }



}
