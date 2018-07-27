package com.cc.ccbootdemo.facade.domain.dataobject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_push")
public class UserPushDO implements Serializable {
    /**
     * user_push_id
     */
    @Id
    @Column(name = "user_push_id")
    private String userPushId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 商户ID
     */
    @Column(name = "merchant_id")
    private String merchantId;

    /**
     * 个推推送标识ID
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * client_id有效状态，1-有效 0-无效
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
     * 获取user_push_id
     *
     * @return user_push_id - user_push_id
     */
    public String getUserPushId() {
        return userPushId;
    }

    /**
     * 设置user_push_id
     *
     * @param userPushId user_push_id
     */
    public void setUserPushId(String userPushId) {
        this.userPushId = userPushId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * 获取个推推送标识ID
     *
     * @return client_id - 个推推送标识ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置个推推送标识ID
     *
     * @param clientId 个推推送标识ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取client_id有效状态，1-有效 0-无效
     *
     * @return available - client_id有效状态，1-有效 0-无效
     */
    public String getAvailable() {
        return available;
    }

    /**
     * 设置client_id有效状态，1-有效 0-无效
     *
     * @param available client_id有效状态，1-有效 0-无效
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