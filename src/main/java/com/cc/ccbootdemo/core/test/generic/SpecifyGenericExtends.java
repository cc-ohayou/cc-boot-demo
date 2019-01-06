package com.cc.ccbootdemo.core.test.generic;

/**
 *
 * * 任何的泛型变量（比如这里的T）都是派生自Object，所以我们在填充泛型变量时，
 * 只能使用派生自Object的类，比如String,Integer,Double，
 * 等而不能使用原始的变量类型，比如int,double,float等。
 然后，问题来了，那在泛型类Point<T>内部，利用泛型定义的变量T x能调用哪些函数呢
 当然只能调用Object所具有的函数，因为编译器根本不知道T具体是什么类型，只有在运行时，
 用户给什么类型，他才知道是什么类型
 如果我们想要这个类型能具有一些除Object外的特征的话 需要进行类型绑定

 <T extends BoundingType> 类型绑定
 举例来讲集合类型
 <T extends Comparable>
 添加上extends Comparable之后，T就可以使用Comparable里的函数了

 * @AUTHOR CF
 * @DATE Created on 2018/12/11 11:33.
 */
public class SpecifyGenericExtends<T extends Comparable> {

    public  int min(T a,T b){
        if(a!=null&&b!=null){
            return a.compareTo(b);
        }else{
            return -1;
        }
    }




    public static void main(String[] args) {
        System.out.println(new SpecifyGenericExtends().min(2,3));
        System.out.println(new SpecifyGenericExtends().min("2333","2333"));

    }



}
