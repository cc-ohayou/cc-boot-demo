package com.qunhe.toilet.facade.domain.common.constants;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 16:47.
 */
public class CommonConstants {
    /**
     * 个推推送相关配置
     */
    public static final String GETUI_host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static String GLOBAL_RESOURCES_DIR = "GLOBAL_RESOURCES_DIR";

    public static void main(String[] args) {
        System.out.println(System.getenv(GLOBAL_RESOURCES_DIR));
        System.out.println(System.getenv());

    }
}
