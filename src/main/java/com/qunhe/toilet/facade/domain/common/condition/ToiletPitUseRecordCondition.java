package com.qunhe.toilet.facade.domain.common.condition;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author bupo
 * @DATE 2020/8/21 15:48
 * @Description
 */
@Data
@Accessors(chain=true)
public class ToiletPitUseRecordCondition {

    private Long pitId;

    private Long uid;

    private Boolean isDelete;


}
