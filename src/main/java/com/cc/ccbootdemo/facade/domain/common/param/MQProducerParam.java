package com.cc.ccbootdemo.facade.domain.common.param;

import lombok.Data;

/**
 * @author CF
 * @DATE Created on 2018/12/7 17:01.
 */
@Data

public class MQProducerParam {
    private String  topic;
    private String tags;
    private String message;



}
