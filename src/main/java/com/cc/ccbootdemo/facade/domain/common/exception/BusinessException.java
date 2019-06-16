package com.cc.ccbootdemo.facade.domain.common.exception;

/**
 * 业务异常类
 *
 * @author CF create on 2017/10/18 9:51
 * @description
 */
public class BusinessException extends BizException {



    public BusinessException(String errorCode, String errorMessage) {
        super(errorCode,errorMessage);

    }
    public BusinessException( String errorMessage) {
        super( ExceptionCode.BIZ_ERROR,errorMessage);

    }

}