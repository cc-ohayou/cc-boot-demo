package com.cc.ccbootdemo.facade.domain.common.param;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/28 14:14.
 */
@Data
public class ResetPwdParam {
    private String mail;
    private String pwd;
    private String verifyCode;

}
