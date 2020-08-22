package com.qunhe.toilet.facade.domain.common.exception;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/1 16:01.
 */
public class BizException extends RuntimeException {

    private Object data;
    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BizException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
