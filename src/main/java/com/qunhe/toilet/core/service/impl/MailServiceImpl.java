package com.qunhe.toilet.core.service.impl;

import com.qunhe.toilet.core.service.MailService;
import com.qunhe.toilet.facade.domain.common.dataobject.mail.MailInfo;
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
      /*  String[] toMailAddresses={"ohayousekai@sina.com","840794748@qq.com"};
        String verifyCode=RandomStringUtil.generateString(6);
        info.setContent("您此次的验证码是："+ verifyCode);
        info.setFrom("ohayousekai@sina.com");
        info.setTo(toMailAddresses);
        info.setSubject("您此次的验证码是："+verifyCode);*/

        MimeMessage mimeMessage = myMailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码 同时设置发送的格式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(info.getFrom());
        messageHelper.setTo(info.getTo());
//        邮件主题
        messageHelper.setSubject(info.getSubject());
//        邮件内容
        messageHelper.setText(info.getContent(), true);


//        发送时间 不设置默认立即发送
//        messageHelper.setSentDate();
        myMailSender.send(mimeMessage);
    }
}
