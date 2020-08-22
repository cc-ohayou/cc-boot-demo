package com.qunhe.toilet.core.mapper.master;

import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfo;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ToiletPitInfoMapper {
    long countByExample(ToiletPitInfoExample example);

    int deleteByExample(ToiletPitInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ToiletPitInfo record);

    int insertSelective(ToiletPitInfo record);

    List<ToiletPitInfo> selectByExample(ToiletPitInfoExample example);

    ToiletPitInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ToiletPitInfo record, @Param("example") ToiletPitInfoExample example);

    int updateByExample(@Param("record") ToiletPitInfo record, @Param("example") ToiletPitInfoExample example);

    int updateByPrimaryKeySelective(ToiletPitInfo record);

    int updateByPrimaryKey(ToiletPitInfo record);
}