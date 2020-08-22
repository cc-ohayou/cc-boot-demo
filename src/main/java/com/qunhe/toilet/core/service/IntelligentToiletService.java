package com.qunhe.toilet.core.service;

import com.qunhe.toilet.facade.domain.common.param.TolietParam;
import com.qunhe.toilet.facade.domain.vo.FloorPitInfoVo;

import java.util.List;

/**
 * @Author bupo
 * @DATE 2020/8/21 13:41
 * @Description
 */
public interface IntelligentToiletService {
    /**
     *
     * @return
     */
    long dealToiletPitChangeAction(TolietParam param);

    List<FloorPitInfoVo> getPitList(TolietParam param);

    long getPitTime(TolietParam param);

    /**
     * 订阅坑位发消息
     * @param param
     * @return
     */
    boolean subPit(TolietParam param);

    /**
     *
     * @param msg
     * @return
     */
    boolean doSubPitEvent(String msg);
}
