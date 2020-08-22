package com.qunhe.toilet.facade.domain.common.param;

import com.qunhe.toilet.facade.domain.common.util.JsonUtil;
import lombok.Data;


/**
 * @Author bupo
 * @DATE 2020/8/20 18:59
 * @Description
 */
@Data
public class TolietParam {
    /**
     * 占用 true  释放 false
     */
    private boolean status;
    /**
     *  建筑编号   1    1号楼
     */
    private Long buildNo;
    /**
     *  楼层号
     */
    private Integer floorNo;
    /**
     * 卫生间房间编号 可能一层有多个
     */
    private Integer roomNo;
    /**
     * 0 女 1 男   2 共用
     */
    private Integer sexType;
    /**
     * 坑位编号 1
     */

    private Integer pitNo;
    /**
     * 花名
     */
    private String ldap;


    public static void main(String[] args) {
        TolietParam  param  = new TolietParam();
        param.setStatus(false);
        param.setBuildNo(1L);
        param.setFloorNo(8);
        param.setRoomNo(1);
        param.setSexType(1);
        param.setPitNo(1);
        System.out.println(JsonUtil.write(param));
    }
}
