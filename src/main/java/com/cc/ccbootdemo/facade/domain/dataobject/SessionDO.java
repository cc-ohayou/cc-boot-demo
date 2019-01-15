package com.cc.ccbootdemo.facade.domain.dataobject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "session")
public class SessionDO implements Serializable {
    /**
     * Session表ID
     */
    @Id
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 商户号,关联merchant_info表merchant_id
     */
    @Column(name = "merchant_id")
    private String merchantId;

    /**
     * 用户ID,关联user表user_id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * Session ID
     */
    private String sid;

    /**
     * 失效时间
     */
    @Column(name = "expire_time")
    private String expireTime;

    /**
     * 来源 web:web,ios:ios,android:android weixin:weixin
     */
    private String source;

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
     * 获取Session表ID
     *
     * @return session_id - Session表ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 设置Session表ID
     *
     * @param sessionId Session表ID
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 获取商户号,关联merchant_info表merchant_id
     *
     * @return merchant_id - 商户号,关联merchant_info表merchant_id
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户号,关联merchant_info表merchant_id
     *
     * @param merchantId 商户号,关联merchant_info表merchant_id
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取用户ID,关联user表user_id
     *
     * @return user_id - 用户ID,关联user表user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID,关联user表user_id
     *
     * @param userId 用户ID,关联user表user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取Session ID
     *
     * @return sid - Session ID
     */
    public String getSid() {
        return sid;
    }

    /**
     * 设置Session ID
     *
     * @param sid Session ID
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 获取失效时间
     *
     * @return expire_time - 失效时间
     */
    public String getExpireTime() {
        return expireTime;
    }

    /**
     * 设置失效时间
     *
     * @param expireTime 失效时间
     */
    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取来源 web:web,ios:ios,android:android weixin:weixin
     *
     * @return source - 来源 web:web,ios:ios,android:android weixin:weixin
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置来源 web:web,ios:ios,android:android weixin:weixin
     *
     * @param source 来源 web:web,ios:ios,android:android weixin:weixin
     */
    public void setSource(String source) {
        this.source = source;
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