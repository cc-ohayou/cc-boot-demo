package com.cc.ccbootdemo.facade.domain.dataobject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "push_config")
public class PushConfigDO implements Serializable {
    /**
     * push_config_id
     */
    @Id
    @Column(name = "push_config_id")
    private String pushConfigId;

    /**
     * 商户ID
     */
    @Column(name = "merchant_id")
    private String merchantId;

    /**
     * 环境，1-测试 2-正式
     */
    private String environment;

    /**
     * appID
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * appSecret
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * appKey
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * masterSecret
     */
    @Column(name = "master_secret")
    private String masterSecret;

    /**
     * 有效状态，1-有效 0-无效
     */
    private String available;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取push_config_id
     *
     * @return push_config_id - push_config_id
     */
    public String getPushConfigId() {
        return pushConfigId;
    }

    /**
     * 设置push_config_id
     *
     * @param pushConfigId push_config_id
     */
    public void setPushConfigId(String pushConfigId) {
        this.pushConfigId = pushConfigId;
    }

    /**
     * 获取商户ID
     *
     * @return merchant_id - 商户ID
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户ID
     *
     * @param merchantId 商户ID
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取环境，1-测试 2-正式
     *
     * @return environment - 环境，1-测试 2-正式
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * 设置环境，1-测试 2-正式
     *
     * @param environment 环境，1-测试 2-正式
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * 获取appID
     *
     * @return app_id - appID
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appID
     *
     * @param appId appID
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取appSecret
     *
     * @return app_secret - appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置appSecret
     *
     * @param appSecret appSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * 获取appKey
     *
     * @return app_key - appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置appKey
     *
     * @param appKey appKey
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 获取masterSecret
     *
     * @return master_secret - masterSecret
     */
    public String getMasterSecret() {
        return masterSecret;
    }

    /**
     * 设置masterSecret
     *
     * @param masterSecret masterSecret
     */
    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    /**
     * 获取有效状态，1-有效 0-无效
     *
     * @return available - 有效状态，1-有效 0-无效
     */
    public String getAvailable() {
        return available;
    }

    /**
     * 设置有效状态，1-有效 0-无效
     *
     * @param available 有效状态，1-有效 0-无效
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}