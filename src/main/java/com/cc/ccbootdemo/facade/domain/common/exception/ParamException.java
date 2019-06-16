package com.cc.ccbootdemo.facade.domain.common.exception;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/20 19:54.
 */
public class ParamException extends BizException {



    public ParamException(String errorCode, String errorMessage) {
        super(errorCode,errorMessage);

    }
    public ParamException( String errorMessage) {
        super( ExceptionCode.PARAM_ERROR,errorMessage);

    }

}
