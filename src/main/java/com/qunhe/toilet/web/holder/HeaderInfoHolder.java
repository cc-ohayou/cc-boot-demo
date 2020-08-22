package com.qunhe.toilet.web.holder;

import org.springframework.core.NamedThreadLocal;

/**
 * @Author bupo
 * @DATE 2020/8/20 16:32
 * @Description 
 */
public abstract class HeaderInfoHolder {

    private static final ThreadLocal<HeaderInfo> headerInfoThreadLocalHolder = new NamedThreadLocal("headinfo");

    public HeaderInfoHolder() {
    }

    public static void setHeaderInfo(HeaderInfo info) {
        headerInfoThreadLocalHolder.set(info);
    }

    public static HeaderInfo getHeaderInfo(){
        return headerInfoThreadLocalHolder.get();
    }

    public static String getToken(){
        return headerInfoThreadLocalHolder.get().getToken();
    }

    public static String getMerchantId(){
        return headerInfoThreadLocalHolder.get().getMerchantid();
    }

    public static String getDeviceId() {
        return headerInfoThreadLocalHolder.get().getDeviceid();
    }

    public static String getUserId(){
        return headerInfoThreadLocalHolder.get().getUserid();
    }

    public static String getSid() {
        return headerInfoThreadLocalHolder.get().getSid();
    }

    public static String getSource() {
        return headerInfoThreadLocalHolder.get().getSource();
    }

    public static String getSoftVersion() {
        return headerInfoThreadLocalHolder.get().getSoftversion();
    }

    public static String getSystemVersion() {
        return headerInfoThreadLocalHolder.get().getSystemversion();
    }

    public static String getClientType() {
        return headerInfoThreadLocalHolder.get().getClienttype();
    }

    public static String getIp() {
        return headerInfoThreadLocalHolder.get().getIp();
    }

    public static String getAppIdentifier() {
        return headerInfoThreadLocalHolder.get().getAppidentifier();
    }

    public static String getPushClientId() {
        return headerInfoThreadLocalHolder.get().getPushclientid();
    }

    public static String getSystemType() {
        return headerInfoThreadLocalHolder.get().getSystemtype();
    }

    // 安卓渠道分发的渠道码【如：腾讯应用宝 000002】
    public static String getChannel() {
        return headerInfoThreadLocalHolder.get().getChannel();
    }
}
