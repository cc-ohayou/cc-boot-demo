package com.qunhe.toilet.facade.domain.common.util;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/7/11 10:48.
 */


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtil {
    private static Logger logger= LoggerFactory.getLogger(ConvertUtil.class);
    /**
     * 手机号码校验正则
     */
    private static final Pattern PHONE_REGEX = Pattern.compile("^(0|86|17951)?1[0-9]{10}$");
    /**
     * JavaScript 校验正则和pattern
     */
    private static final String SCRIPT_REGEX = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
    // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
    private static final Pattern SCRIPT_PATTERN = Pattern.compile(SCRIPT_REGEX, Pattern.CASE_INSENSITIVE);
    /**
     * style校验正则和pattern
     */
    private static final String STYLE_REGEX = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
    // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
    private static final Pattern STYLE_PATTERN = Pattern.compile(STYLE_REGEX, Pattern.CASE_INSENSITIVE);
    /**
     * html匹配正则和pattern
     */
    private static final String HTML_REGEX = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final Pattern HTML_PATTERN = Pattern.compile(HTML_REGEX, Pattern.CASE_INSENSITIVE);
    /**
     * html匹配正则和pattern 1
     */
    private static final String HTML_REGEX_TAIL = "<[^>]+";
    private static final Pattern HTML_TAIL_PATTERN = Pattern.compile(HTML_REGEX_TAIL, Pattern.CASE_INSENSITIVE);
    /**
     * 去掉文本中的html标签
     *
     * @param
     * @return
     */
    private static String htmlToText(String htmlStr) {

        if (!StringUtil.isEmpty(htmlStr)) {
            try {
                // 过滤script标签
                Matcher scriptMatcher = SCRIPT_PATTERN.matcher(htmlStr);
                htmlStr = scriptMatcher.replaceAll("");
                // 过滤style标签
                Matcher styleMatcher = STYLE_PATTERN.matcher(htmlStr);
                htmlStr = styleMatcher.replaceAll("");
                // 过滤html标签
                Matcher htmlMatcher = HTML_PATTERN.matcher(htmlStr);
                htmlStr = htmlMatcher.replaceAll("");
                // 过滤html标签2
                Matcher htmlMatcher2 = HTML_TAIL_PATTERN.matcher(htmlStr);
                htmlStr = htmlMatcher2.replaceAll("");
                htmlStr = htmlStr.replaceAll("&amp;", "").replaceAll("nbsp;", "");
            } catch (Exception e) {
                logger.warn("Html2Text exception warn: " + e.getMessage());
            }
        }else{
            return "";
        }
        // 返回文本字符串
        return htmlStr;
    }

    private static boolean isPhoneValid(String mobileNumber) {
        boolean flag ;
        try {
            Matcher matcher = PHONE_REGEX.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(Object obj) {
        return toInt(obj.toString(), 0);
    }

    private static int toInt(String str, int defVal) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defVal;
        }
    }
    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    private static float toFloat(String str, float defVal) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static double toDouble(Object obj) {
        return toDouble(obj.toString(), 0.0d);
    }

    private static double toDouble(String str, double defVal) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    static long toLong(String str) {
        return toLong(str, 0l);
    }

    private static long toLong(String str, long defVal) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static BigDecimal toDecimal(String str) {
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static String toString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    public static String toBase64(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    public static byte[] toByte(String str) {
        return Base64.decodeBase64(str);
    }

    /**
     * @description
     * @author CF create on 2018/3/12 15:00
     */
    public static String firstEmptyReturnTwo(String firstStr, String two) {
        return StringUtils.isEmpty(firstStr)? two:firstStr;
    }

    public static String parsePhone(String phone) {

            if(StringUtils.isEmpty(phone)||!isPhoneValid(phone)) {
                return "";
            }
            return phone.substring(0,3).concat("****").concat(phone.substring(7,11));
        }

    public static  int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }




    public static void main(String[] args) {
        htmlToText("<span><a href=\"javascript:alert(123);\">点点看</a></span>");
    }
}
