package com.qunhe.toilet.core.service;

import com.qunhe.toilet.facade.domain.common.dataobject.mail.MailInfo;

import javax.mail.MessagingException;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/18 18:08.
 */

public interface MailService {
    void sendMail(MailInfo info) throws MessagingException;
}
