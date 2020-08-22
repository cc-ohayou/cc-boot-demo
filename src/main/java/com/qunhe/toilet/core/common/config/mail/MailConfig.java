package com.qunhe.toilet.core.common.config.mail;

import com.qunhe.toilet.core.common.properties.resource.TestLoadResource;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/18 18:14.
 */
@Configuration
@ConditionalOnBean(TestLoadResource.class)
public class MailConfig {
    @Bean
    public MailSender myMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //指定用来发送Email的邮件服务器主机名
        mailSender.setHost(TestLoadResource.propertyJdbc.getProperty("mailHost"));
        //默认端口，标准的SMTP端口 25 可不设置 使用网易邮箱时可不设置
        mailSender.setUsername(TestLoadResource.propertyJdbc.getProperty("mailUser"));
        mailSender.setPassword(TestLoadResource.propertyJdbc.getProperty("mailPass"));
        mailSender.setDefaultEncoding("Utf-8");
        mailSender.setProtocol(TestLoadResource.propertyJdbc.getProperty("mailProtocol"));
       if("yes".equals(TestLoadResource.propertyJdbc.getProperty("mailSSLSign"))){
           setSSLConfig(mailSender);
           mailSender.setPort(Integer.parseInt(TestLoadResource.propertyJdbc.getProperty("mailPort")));
       }
        return mailSender;
    }

    private void setSSLConfig(JavaMailSenderImpl mailSender) {
        Properties  props=new Properties();
        MailSSLSocketFactory sslSocketFactory = null;
        try {
            sslSocketFactory=new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        assert sslSocketFactory != null;
        sslSocketFactory.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable",true);
        props.put("mail.smtp.ssl.socketFactory",sslSocketFactory);
        mailSender.setJavaMailProperties(props);

    }


}
