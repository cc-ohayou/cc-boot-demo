package com.qunhe.toilet.facade.domain.bizobject.param;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/23 10:20.
 */
@Data
public class RegistParam {
    private String phone;
    private String nickName;
    private String pwd;
    private String question;
    private String answer;
    private String mail;


}
