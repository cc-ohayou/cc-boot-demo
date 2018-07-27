package com.cc.ccbootdemo.facade.domain.bizobject.strgy;

import com.cc.ccbootdemo.facade.domain.common.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/7/10 19:24.
 */
@Data
public class StrgyBiz {
    private String tradeType;
    private String stockName;
    private String stockCode;
    private String quantity;
    private String price;
    //买入用户名
    private String userName;
    //交易状态
    private String status;
    private Date createTime;


    @Override
    public String toString(){
      return   "类型:"+tradeType+",股票名称:"+"stockName"+",股票代码:"+stockCode+",数量:"+quantity
                +",创建时间:"+ DateUtil.standardFormat(createTime);
    }
}
