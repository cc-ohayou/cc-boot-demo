package com.qunhe.toilet.facade.domain.bizobject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/15 14:11.
 */
public class BaseResult implements Serializable{

    private int code;

    private Object data;

    private String msg;

    public BaseResult() {
        this(0, null, "");
    }

    public BaseResult(Object data) {
        this(0, data, "");
    }

    public BaseResult(int code, String message) {
        this(code, null, message);
    }

    public BaseResult(int code, Object data, String message) {
        this.code = code;
        this.data = data != null ? data : new HashMap<String, String>();
        this.msg = message != null ? message : "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}
