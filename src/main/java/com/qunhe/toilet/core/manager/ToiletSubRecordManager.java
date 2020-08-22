package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.common.condition.ToiletPitSubRecordCondition;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitSubRecord;

import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 16:19
 * @Description
 */
public interface ToiletSubRecordManager {

    boolean  addRecord(ToiletPitSubRecord  record);

    List<ToiletPitSubRecord> selectByCOndition(ToiletPitSubRecordCondition condition);

    boolean  updateByCondition(ToiletPitSubRecord record,ToiletPitSubRecordCondition  condition);

}
