package com.cc.ccbootdemo.core.test.generic;

import lombok.Data;

/**
 * @AUTHOR CF
 * 传进去多个泛型
 *
 * 任意一个大写字母都可以。他们的意义是完全相同的，但为了提高可读性，大家还是用有意义的字母比较好，一般来讲，在不同的情境下使用的字母意义如下：
   E — Element，常用在java Collection里，如：List<E>,Iterator<E>,Set<E>
   K,V — Key，Value，代表Map的键值对
   N — Number，数字
   T — Type，类型，如String，Integer等等

 * @DATE Created on 2018/12/11 10:51.
 */
@Data
public class MorePoint<T,U,W> {

    private T x;
    private U y;
    private W z;


    public static void main(String[] args) {
        MorePoint<String,Integer,Float> morePoint=new MorePoint<>();
        morePoint.setX("");
        morePoint.setY(1);
        morePoint.setZ(1.0f);
    }

}
