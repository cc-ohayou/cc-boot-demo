package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.bizobject.param.OperListQueryParam;
import com.cc.ccbootdemo.facade.domain.dataobject.OperateBiz;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OperateBizDao extends     Mapper<OperateBiz> {

    int insertSelectiveMy(@Param("pojo") OperateBiz pojo);

    int insertList(@Param("pojos") List< OperateBiz> pojo);

    List<OperateBiz> selectBySelective(@Param("pojo") OperateBiz pojo);

    int update(@Param("pojo") OperateBiz pojo);

    long getOperBizTotalCou(@Param("pojo")OperListQueryParam param);

    List<OperateBiz> getOperBizList(@Param("pojo")OperListQueryParam param);
}
