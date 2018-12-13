package com.cc.ccbootdemo.core.test.generic;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR CF
 * 普通类使用泛型函数
 * 泛型必须继承自Object类
 * @DATE Created on 2018/12/11 11:13.
 */
@Data
public class GenericFanc {
    private String x;

    public GenericFanc(String x) {
        this.x = x;
    }

    public  static  <T>  void staticMethod(T t){
        System.out.println(t.toString());
    }
    public    <T>  void generalMethod(T t){
        System.out.println(t.toString());
    }

    /**
     * @description 使用Class<T>传递泛型类Class对象
     * @author CF create on 2018/12/11 11:23
     */
    public static <T> List<T> parseArray(String response, Class<T> object){
        System.out.println(response);
        List<T> modelList = JSON.parseArray(response, object);
        return modelList;
    }


    /*

    定义泛型数组
    */
    public static <T> T[] fun1(T...arg){

        return arg ;
    }



    public static void main(String[] args) {
        GenericFanc.<String>staticMethod("hahaha");
        GenericFanc genericFanc=new GenericFanc("2");
        // 最好这么用 前面直接指明方法的参数 说明调用的这个函数是泛型函数
        // 灰色下划线不用多管

        genericFanc.<Integer>generalMethod(12);
        genericFanc.generalMethod(12);
        List<GenericFanc> list=new ArrayList<>();
        list.add(new GenericFanc("1"));
        list.add(new GenericFanc("2"));
        String str=JSON.toJSONString(list);

        parseArray(str,GenericFanc.class);

        Integer i[] = fun1(1,2,3,4,5,6) ;
        Integer[] result = fun1(i) ;
        String j[]=fun1("1","2","3");
        String[] result2 = fun1(j) ;

    }

}
