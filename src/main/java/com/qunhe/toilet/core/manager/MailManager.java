package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.common.dataobject.mail.MailInfo;

import javax.mail.MessagingException;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/28 11:57.
 */
public interface MailManager {
    void sendMail(MailInfo info) throws MessagingException;

}
