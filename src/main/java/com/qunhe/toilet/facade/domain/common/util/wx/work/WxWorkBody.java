package com.qunhe.toilet.facade.domain.common.util.wx.work;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
@Data
@Accessors(chain = true)
public class WxWorkBody {

    private List<String> toUser;

    private List<String> toParty;

    private List<String> toTag;

    private String agentId;

}
