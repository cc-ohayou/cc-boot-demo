package com.cc.ccbootdemo.facade.domain.bizobject.param;

/**
 * 对应header key中的商户id   客户端类型、用户id
 *
 * @AUTHOR CF
 * @DATE Created on 2017/9/24 20:06.
 */
public class HeaderParam {
    private String clientType;
    private String merchantId;
    private String userId;
    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
