package com.qunhe.toilet.facade.domain.common.util;


import com.qunhe.toilet.facade.domain.common.exception.BusinessException;
import com.qunhe.toilet.facade.domain.common.exception.ParamException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/20 13:34.
 */
public class AssertUtil {
    public static final String PARAM_ERROR = "1001";//参数异常
    public static final String BIZ_ERROR = "1002";//业务异常
    /**
     * @description 一般用于判断一些查询返回map必须不为空的情况 譬如人员信息
     * @author CF create on 2017/6/26 16:02
     */
    public static void isNullList(List list, String code, String msg) throws ParamException {

        if (list == null || list.size() == 0) {

            throw new ParamException(code, msg);
        }

    }

    /**
     * @description 此方法多用于判断传入参数非空  属于ParameterException
     * @author CF create on 2017/6/19 15:15
     */
    public static void isNullStr(String str, String code, String msg) throws ParamException {

        if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
            throw new ParamException(code, msg);
        }
    }
    public static void isNullStr(String str,  String msg) throws ParamException {

        if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
            throw new ParamException(PARAM_ERROR, msg);
        }
    }

    public static void isNullObj(Object obj, String code, String msg) throws ParamException {
        if (obj == null) {
            throw new ParamException(code, msg);
        }
    }
    public static void isNullObj(Object obj,String msg) throws ParamException {
        if (obj == null) {
            throw new ParamException(PARAM_ERROR, msg);
        }
    }

    /**
     * @description 判断业务引起的异常
     * @author CF create on 2017/6/26 16:04
     */
    public static void isTrueBIZ(Boolean obj, String code, String msg) throws BusinessException {
        if (obj) {
            throw new BusinessException(code, msg);
        }

    }

    public static void isTrueBIZ(Boolean obj,  String msg) throws BusinessException {
        if (obj) {
            throw new BusinessException(BIZ_ERROR, msg);
        }

    }

    public static void isTrueParam(Boolean obj, String msg) throws ParamException {
        if (obj) {
            throw new ParamException(PARAM_ERROR, msg);
        }

    }

    /**
     * @description 判断是否是数字  非数字则抛出异常
     * @author CF create on 2017/6/26 16:08
     */
    public static void isNumber(String num, String code, String msg) throws ParamException {
        String regEx = "^[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(num);
        if (!mat.find()) {
            throw new ParamException(code, msg);
        }

    }

    public static void isNullParamStr(String str, String msg) throws ParamException {
        if (str == null || "".equals(str.trim()) || "null".equals(str.trim())) {
            throw new ParamException(PARAM_ERROR, msg);
        }

    }

    public static void isNullParamObj(String obj, String msg) throws ParamException {
        if (obj == null) {
            throw new ParamException(PARAM_ERROR, msg);
        }
    }
}
