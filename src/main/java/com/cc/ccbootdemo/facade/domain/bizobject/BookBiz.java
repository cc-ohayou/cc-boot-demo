package com.cc.ccbootdemo.facade.domain.bizobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/28 15:50.
 */
@Data
@EqualsAndHashCode
public class BookBiz {
    private String id;
    private String name;
    private String author;
    private int pages;
    private double price;
    private String url;
    private String label;
    /**
     * productId : 25263052
     * title : 小小小小的火 （《无声告白》作者伍绮诗重磅新作）
     * image : http://img3m2.ddimg.cn/34/7/25263052-1_x_3.jpg
     * originalPrice : 52
     * exchangePrice : 9.9
     * isEBook : 0
     * isSpu : 0
     * isMall : 0
     * idx : 1
     */

    private String productId;
    private String title;
    private String image;
    private String originalPrice;
    private String exchangePrice;
    private String isEBook;
    private String isSpu;
    private String isMall;
    private String idx;


    public static void main(String[] args) {






    }

}
