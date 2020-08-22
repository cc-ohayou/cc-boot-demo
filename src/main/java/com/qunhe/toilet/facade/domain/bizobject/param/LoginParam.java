package com.qunhe.toilet.facade.domain.bizobject.param;

import lombok.Data;

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
//    @IsMobile
    private String phone;
    @NotNull
//    @Length(min=6)
    private String pwd;
}
