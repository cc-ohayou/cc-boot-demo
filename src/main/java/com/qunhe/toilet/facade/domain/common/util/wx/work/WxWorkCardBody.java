package com.qunhe.toilet.facade.domain.common.util.wx.work;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Function: ${Description}
 *
 * @author chaomeng
 * @date 2019/12/11
 */
@Data
@Accessors(chain = true)
public class WxWorkCardBody extends WxWorkBody {

    private String title;

    private String description;

    private String url;

    private String buttonText;

}