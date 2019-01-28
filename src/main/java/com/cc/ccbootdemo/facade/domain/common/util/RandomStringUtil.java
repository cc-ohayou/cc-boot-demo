package com.cc.ccbootdemo.facade.domain.common.util;

import java.util.Random;

/**
 * 生成随机字符串的方法
 *
 * @AUTHOR xianghy
 * @DATE Created on 2018/3/12 14:00.
 */
public class RandomStringUtil {

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALLCHAR_LOWERCASE = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static final String ALL_NUM = "0123456789";

    /**
     * 返回一个定长的随机字符串(包含小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR_LOWERCASE.charAt(random.nextInt(ALLCHAR_LOWERCASE.length())));
        }
        return sb.toString();
    }

    public static String generateNum(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_NUM.charAt(random.nextInt(ALL_NUM.length())));
        }
        return sb.toString();
    }

}
