package com.qunhe.toilet.core.manager;

import com.qunhe.toilet.facade.domain.common.condition.ToiletPitInfoCondition;
import com.qunhe.toilet.facade.domain.dataobject.ToiletPitInfo;

import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 14:42
 * @Description
 */
public interface ToiletPitInfoManager {

      boolean  addRecord(ToiletPitInfo info);

      boolean  updateInfoByCondition(ToiletPitInfo tolietPitInfo, ToiletPitInfoCondition condition);

      boolean  updateInfoByPrimaryKey(ToiletPitInfo tolietPitInfo);

      List<ToiletPitInfo> selectListByCondition(ToiletPitInfoCondition condition);
}
