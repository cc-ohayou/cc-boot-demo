package com.cc.ccbootdemo.facade.domain.bizobject.param;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/14 10:22.
 */
@Data
public class LoginParam {
    private String userName;
    private String mail;
    private String phone;
    private String pwd;
}
