package com.qunhe.toilet.core.mapper.master;

import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecord;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ToiletPitSubRecordMapper {
    long countByExample(ToiletPitSubRecordExample example);

    int deleteByExample(ToiletPitSubRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ToiletPitSubRecord record);

    int insertSelective(ToiletPitSubRecord record);

    List<ToiletPitSubRecord> selectByExample(ToiletPitSubRecordExample example);

    ToiletPitSubRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ToiletPitSubRecord record, @Param("example") ToiletPitSubRecordExample example);

    int updateByExample(@Param("record") ToiletPitSubRecord record, @Param("example") ToiletPitSubRecordExample example);

    int updateByPrimaryKeySelective(ToiletPitSubRecord record);

    int updateByPrimaryKey(ToiletPitSubRecord record);
}