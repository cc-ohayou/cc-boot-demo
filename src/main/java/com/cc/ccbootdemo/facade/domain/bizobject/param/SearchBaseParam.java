package com.cc.ccbootdemo.facade.domain.bizobject.param;



import lombok.Data;


/**用于搜索查询列表的参数对象 包含常见查询条件
 * @AUTHOR CF
 * @DATE Created on 2017/10/18 10:39.
 */
@Data
public class SearchBaseParam {
    private String phone;
    private String name;
    private String startTime;
    private String endTime;
    private String pageSize;//每页大小
    private int pageNum;//页数
    private int currPage;//当前页
    private int offset;//数据库查询起始数
    private int size=10;//数据库limit查询条数
    private int totalCount;


}
