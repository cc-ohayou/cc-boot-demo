package com.qunhe.toilet.facade.domain.common.param;

import com.qunhe.toilet.facade.domain.bizobject.param.HeaderParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/30 10:16.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GateWayReqParam extends HeaderParam {
    private String operId;
    private String gateWayCode;
//    private String operId;
}
