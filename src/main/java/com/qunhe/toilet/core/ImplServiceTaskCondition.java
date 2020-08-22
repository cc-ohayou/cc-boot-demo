package com.qunhe.toilet.core;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/18 18:09
 * @Description
 */
@Data
public class ImplServiceTaskCondition extends BasePageCondition{

    /**
     * 创建起始时间
     */
    private Date createStartTime;
    /**
     * 创建截止时间
     */
    private Date createEndTime;
    /**
     * 工单状态
     */
    private String taskSopStatus;
    /**
     *
     */
    private List<String> taskSopStatusList;
    /**
     * 客户全名
     */
    private String fullName ;
    /**
     * 客户姓名列表
     */
    private List<String> customerNameList ;
    /**
     * 实施工单开始起始时间
     */
    private Date beginStartTime;
    /**
     * 实施工单开始截止时间
     */
    private Date beginEndTime;
    /**
     * 工单结束 起始时间
     */
    private Date finishStartTime;
    /**
     * 工单结束 截止时间
     */
    private Date finishEndTime;
    /**
     * 所属行业 一级行业  举例 1
     */
    private String industryType;

    /**
     * 实施工程师的用户id
     */
    private Long paramOperation;
    /**
     * 实施运营用户名  举例 吥破
     */
    private String operationName;
    /**
     * 创建人 id    举例 5670
     */
    private String submitor;
    /**
     * 当前审批人id 举例 5670
     */
    private String approver;
    /**
     *
     */
    private String taskNO;

    private List<String> taskNOList;
    /**
     * 任务状态
     */
    private List<Integer> taskStates;

    private Integer taskState;
    /**
     * 审批人
     */
    private List<String> approverIds;
    /**
     * 负责人  实施工程师
     */
    private List<Long> paramOperationIds;
    /**
     *
     */
    private List<String> submitorIds;
    /**
     *
     */
    private Integer showRange=0;
    /**
     * showRange  结合submitSign来决定如何查询 用于查询谁发起  设置对应的submitors
     */
    private boolean submitSign;
    /**
     * 审批人标识 true意味查询时人员限制以approver为准
     */
    private boolean approverTabSign;
    /**
     * 负责tab的标识 用于关联查询 实施工单人员   设置对应的
     */
    private boolean chargeTabSign;
    /**
     * 查询离职的需要用到关联查询
     */
    private boolean leaveOfficeStaffQuerySign;



    /**
     * 排序
     */
    private String orderByClause ;

    public Page getQueryPage(){
        if(this.getPage()!=null&&this.getSize()!=null){
            return new Page(this.getPage(),this.getSize());
        }else{
            return null;
        }
    }
}
