package com.cc.ccbootdemo.core.manager.impl;

import com.cc.ccbootdemo.core.manager.MailManager;
import com.cc.ccbootdemo.facade.domain.common.dataobject.mail.MailInfo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/28 11:57.
 */
@Component
public class MailManagerImpl implements MailManager{



    @Resource
    private JavaMailSender myMailSender;

    @Override
    public void sendMail(MailInfo info) throws MessagingException {
        /*SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(info.getFrom());//发件人
        message.setTo(info.getTo());//收件人
        message.setSubject(info.getSubject());//主题
        message.setText(info.getContent());//正文
        myMailSender.send(message);*/

        MimeMessage mimeMessage = myMailSender.createMimeMessage();

        // 设置utf-8或GBK编码，否则邮件会有乱码 同时设置发送的格式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(((JavaMailSenderImpl)myMailSender).getUsername());
        messageHelper.setTo(info.getTo());
//        邮件主题
        messageHelper.setSubject(info.getSubject());
//        邮件内容
        messageHelper.setText(info.getContent(), true);
//        messageHelper.
//        messageHelper.
     /*   String[] toMailAddresses={"ohayousekai@sina.com","840794748@qq.com"};

        info.setContent("您此次的验证码是："+ RandomStringUtil.generateString(6));
        info.setFrom("13758080693@163.com");
        info.setTo(toMailAddresses);
        info.setSubject("您此次的验证码是："+ RandomStringUtil.generateString(6));*/

//        发送时间 不设置默认立即发送
//        messageHelper.setSentDate();
        myMailSender.send(mimeMessage);
    }
}
