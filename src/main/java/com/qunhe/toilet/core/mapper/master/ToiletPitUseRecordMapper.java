package com.qunhe.toilet.core.mapper.master;

import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecord;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * @Author bupo
 * @DATE 2020/8/21 16:00
 * @Description
 */
public interface ToiletPitUseRecordMapper {
    long countByExample(ToiletPitUseRecordExample example);

    int deleteByExample(ToiletPitUseRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ToiletPitUseRecord record);

    int insertSelective(ToiletPitUseRecord record);

    List<ToiletPitUseRecord> selectByExample(ToiletPitUseRecordExample example);

    ToiletPitUseRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ToiletPitUseRecord record, @Param("example") ToiletPitUseRecordExample example);

    int updateByExample(@Param("record") ToiletPitUseRecord record, @Param("example") ToiletPitUseRecordExample example);

    int updateByPrimaryKeySelective(ToiletPitUseRecord record);

    int updateByPrimaryKey(ToiletPitUseRecord record);
}