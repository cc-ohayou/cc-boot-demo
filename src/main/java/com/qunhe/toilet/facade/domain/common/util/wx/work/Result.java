package com.qunhe.toilet.facade.domain.common.util.wx.work;

import lombok.Data;

import java.beans.ConstructorProperties;

/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
@Data
public class Result<T> extends AbstractResult {

    public static final String OK_CODE = "0";

    private String c;
    private String m;
    private T d;

    @ConstructorProperties({"c", "m", "d"})
    public Result(String c, String m, T d) {
        this.c = c;
        this.m = m;
        this.d = d;
    }

    public boolean success() {
        return OK_CODE.equals(this.c);
    }

}