package com.qunhe.toilet.facade.domain.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * toilet_pit_use_record
 * @author 
 */
public class ToiletPitUseRecord implements Serializable {
    private Long id;

    private Date createTime;

    /**
     * 关联坑位no
     */
    private Long tolietPitId;

    private Date updateTime;

    /**
     * 0 当前起效   1 无效记录
     */
    private Boolean deleted;

    /**
     * 谁在使用  备用字段
     */
    private Long uid;

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

    public Long getTolietPitId() {
        return tolietPitId;
    }

    public void setTolietPitId(Long tolietPitId) {
        this.tolietPitId = tolietPitId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
}