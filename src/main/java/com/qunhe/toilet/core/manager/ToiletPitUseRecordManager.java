package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.common.condition.ToiletPitUseRecordCondition;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitUseRecord;

import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 15:47
 * @Description
 */
public interface ToiletPitUseRecordManager {

    boolean  addRecord(ToiletPitUseRecord record);

    List<ToiletPitUseRecord> selectByCondition(ToiletPitUseRecordCondition  condition);

    boolean  updateByCondition(ToiletPitUseRecord  target,ToiletPitUseRecordCondition  condition);

}
