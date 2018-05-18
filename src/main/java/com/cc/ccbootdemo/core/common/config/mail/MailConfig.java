package com.cc.ccbootdemo.core.common.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/18 18:14.
 */
@Configuration
public class MailConfig {
    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.163.com");//指定用来发送Email的邮件服务器主机名
        mailSender.setPort(25);//默认端口，标准的SMTP端口
        mailSender.setUsername("13758080693@163.com");//用户名
        mailSender.setPassword("qusiba.0.");//密码
        mailSender.setDefaultEncoding("Utf-8");

        return mailSender;
    }
}
