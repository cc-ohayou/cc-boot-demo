package com.cc.ccbootdemo.facade.domain.bizobject.param;

import com.cc.ccbootdemo.facade.domain.common.annotation.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/14 10:22.
 */
@Data
public class LoginParam extends HeaderParam{
    @NotNull
    private String userName;
    private String mail;
    @IsMobile
    private String phone;
    @NotNull
    @Length(min=6)
    private String pwd;
}
