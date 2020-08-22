package com.qunhe.toilet.facade.domain.common.dataobject.mail;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/5/18 18:10.
 */
@Data
public class MailInfo {
    private String  from;
    private String[]  to;
    private String  subject;
    private String  content;
}
