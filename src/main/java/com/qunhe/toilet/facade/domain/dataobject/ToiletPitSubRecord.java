package com.qunhe.toilet.facade.domain.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * toilet_pit_sub_record
 * @author 
 */
public class ToiletPitSubRecord implements Serializable {
    private Long id;

    private Date createTime;

    private Date updateTime;

    /**
     * 预约起始时间
     */
    private Date notifyStartTime;

    /**
     * 预约时填入的截止时间
     */
    private Date notifyEndTime;

    /**
     * 预约的楼层id
     */
    private Integer floorId;

    /**
     * 0   1 已作废
     */
    private Boolean deleted;

    /**
     * 预约用户id
     */
    private Long uid;

    /**
     * 花名拼音
     */
    private String ldap;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getNotifyStartTime() {
        return notifyStartTime;
    }

    public void setNotifyStartTime(Date notifyStartTime) {
        this.notifyStartTime = notifyStartTime;
    }

    public Date getNotifyEndTime() {
        return notifyEndTime;
    }

    public void setNotifyEndTime(Date notifyEndTime) {
        this.notifyEndTime = notifyEndTime;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getLdap() {
        return ldap;
    }

    public void setLdap(String ldap) {
        this.ldap = ldap;
    }
}