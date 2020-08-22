package com.qunhe.toilet.facade.domain.vo;

import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 17:45
 * @Description
 */
public class FloorPitInfoVo {


    /**
     * floorNo : 8
     * floorId:8
     * tolietRoomList : [{"buildingName":"1号楼","floorNo":8,"roomNo":1,"roomName":"听雨阁 1号","pitNo":1,"usingState":true,"usedTime":10,"sexType":"man","availableSign":true,"inconvenientSign":false},{"buildingName":"1号楼","floorNo":8,"roomNo":1,"roomName":"观瀑亭 2号","pitNo":2,"usingState":true,"usedTime":10,"sexType":"man","availableSign":true,"inconvenientSign":false}]
     */

    private int floorNo;

    private int floorId;
    private List<ToiletPitInfoVo> tolietRoomList;

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(final int floorId) {
        this.floorId = floorId;
    }

    public List<ToiletPitInfoVo> getTolietRoomList() {
        return tolietRoomList;
    }

    public void setTolietRoomList(final List<ToiletPitInfoVo> tolietRoomList) {
        this.tolietRoomList = tolietRoomList;
    }

}
