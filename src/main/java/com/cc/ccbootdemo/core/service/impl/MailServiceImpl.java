package com.cc.ccbootdemo.core.service.impl;

import com.cc.ccbootdemo.core.service.MailService;
import com.cc.ccbootdemo.facade.domain.common.dataobject.mail.MailInfo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/18 18:13.
 */
@Service
public class MailServiceImpl implements MailService{

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
        messageHelper.setFrom(info.getFrom());
        messageHelper.setTo(info.getTo());
        messageHelper.setSubject(info.getSubject());
        messageHelper.setText(info.getContent(), true);
        myMailSender.send(mimeMessage);
    }
}
