package com.cc.ccbootdemo.facade.domain.bizobject.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/11 10:04.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperListQueryParam extends SearchBaseParam{
//    环境类型
    private String  envType;
    private String  operName;
}
