package com.cc.ccbootdemo.core.manager;

import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/22 14:18.
 */
public interface OperateBizManager {

    int insertList(List< OperateBiz> pojos);

    List<OperateBiz> selectBySelective(OperateBiz pojo);

    long getOperBizTotalCou(OperListQueryParam param);

    List<OperateBiz> getOperBizList(OperListQueryParam param);

    OperateBiz getOperBizByPrimary(String operId);

}
