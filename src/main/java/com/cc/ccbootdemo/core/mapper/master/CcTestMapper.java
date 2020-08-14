package com.cc.ccbootdemo.core.mapper.master;

import com.cc.ccbootdemo.facade.domain.dataobject.CcTest;
import com.cc.ccbootdemo.facade.domain.dataobject.CcTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author admin
 */
public interface CcTestMapper {
    long countByExample(CcTestExample example);

    int deleteByExample(CcTestExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CcTest record);

    int insertSelective(CcTest record);

    List<CcTest> selectByExample(CcTestExample example);

    CcTest selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CcTest record, @Param("example") CcTestExample example);

    int updateByExample(@Param("record") CcTest record, @Param("example") CcTestExample example);

    int updateByPrimaryKeySelective(CcTest record);

    int updateByPrimaryKey(CcTest record);
}