package com.qunhe.toilet.facade.domain.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * toilet_pit_info
 * @author 
 */
public class ToiletPitInfo implements Serializable {
    private Long id;

    private Date createTime;

    private Date updateTime;

    /**
     * 1 无效 0有效
     */
    private Boolean deleted;

    /**
     * 1使用中  0空闲
     */
    private Boolean useState;

    /**
     * 0 非残疾卫生间  1 残疾卫生间标识
     */
    private Boolean inconvenientSign;

    /**
     * man 男士卫生间  woman 女士 both  共用型
     */
    private String tolietType;

    /**
     * 建筑号  比如 1号楼  2号 楼
     */
    private Long buildingNo;

    /**
     * 楼层数
     */
    private Integer floorNo;

    /**
     * 房间号  每层可有多个卫生间
     */
    private Integer roomNo;

    /**
     * 坑位号 一个卫生间基本不可能超过1000个 
     */
    private Integer pitPositionNo;

    /**
     * ok   可用  underRepair  修理中 
     */
    private String availableStatus;

    /**
     * 地址信息  空间坐标之类的
     */
    private String addressInfo;

    /**
     * 描述 这是个神奇的坑位
     */
    private String description;

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getUseState() {
        return useState;
    }

    public void setUseState(Boolean useState) {
        this.useState = useState;
    }

    public Boolean getInconvenientSign() {
        return inconvenientSign;
    }

    public void setInconvenientSign(Boolean inconvenientSign) {
        this.inconvenientSign = inconvenientSign;
    }

    public String getTolietType() {
        return tolietType;
    }

    public void setTolietType(String tolietType) {
        this.tolietType = tolietType;
    }

    public Long getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(Long buildingNo) {
        this.buildingNo = buildingNo;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getPitPositionNo() {
        return pitPositionNo;
    }

    public void setPitPositionNo(Integer pitPositionNo) {
        this.pitPositionNo = pitPositionNo;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}