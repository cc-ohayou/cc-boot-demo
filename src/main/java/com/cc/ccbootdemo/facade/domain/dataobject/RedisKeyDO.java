package com.cc.ccbootdemo.facade.domain.dataobject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "redis_key")
public class RedisKeyDO implements Serializable {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * key
     */
    private String key;

    /**
     * 简短的名称
     */
    private String name;

    /**
     * key更加全面的描述
     */
    private String description;

    /**
     * 父级key
     */
    @Column(name = "parent_key")
    private String parentKey;

    /**
     * key类型 hash  set string zset list
     */
    private String type;

    /**
     * key关联的业务类型  譬如说是 结算相关的  存储 ps-settle
     */
    @Column(name = "biz_type")
    private String bizType;

    /**
     * 记录创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取key
     *
     * @return key - key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key
     *
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取简短的名称
     *
     * @return name - 简短的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置简短的名称
     *
     * @param name 简短的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取key更加全面的描述
     *
     * @return description - key更加全面的描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置key更加全面的描述
     *
     * @param description key更加全面的描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取父级key
     *
     * @return parent_key - 父级key
     */
    public String getParentKey() {
        return parentKey;
    }

    /**
     * 设置父级key
     *
     * @param parentKey 父级key
     */
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    /**
     * 获取key类型 hash  set string zset list
     *
     * @return type - key类型 hash  set string zset list
     */
    public String getType() {
        return type;
    }

    /**
     * 设置key类型 hash  set string zset list
     *
     * @param type key类型 hash  set string zset list
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取记录创建时间
     *
     * @return create_time - 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置记录创建时间
     *
     * @param createTime 记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后一次修改时间
     *
     * @return update_time - 最后一次修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置最后一次修改时间
     *
     * @param updateTime 最后一次修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}