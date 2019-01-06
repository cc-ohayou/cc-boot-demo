package com.cc.ccbootdemo.core.test.generic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR CF
 *    泛型使用通配符
 * @DATE Created on 2018/12/11 13:31.
 */
@Data
public class WildCardGeneric <T>{
    private T x;
    private T y;

    public WildCardGeneric(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) {
        /**
         *  ?无界通配符
         *  使得各种类型的 WildCardGeneric 实例都可以赋值给 wildCardGeneric
         *  但也正因为如此 wildCardGeneric 的类型是未知的 所以不能设置某一类型的值进去
         *   wildCardGeneric.setX(1); //编译会报错
         *
         *   泛型变量T不能在代码用于创建变量，只能在类，接口，函数中声明以后，才能使用
         *   而无边界通配符？则只能用于填充泛型变量T，表示通配任何类型！！！！再重复一遍：
         *   ？只能用于填充泛型变量T。它是用来填充T的！！！！只是填充方式的一种！！！
         *   不能用于定义变量
         *
         *   <? super XXX>则表示填充为任意XXX的父类
         */
        WildCardGeneric<? extends Number>  wildCardGeneric;

        wildCardGeneric=new WildCardGeneric<>(1,2);
//        wildCardGeneric=new WildCardGeneric<Integer,Integer>(1,2);//便以同样报错
        wildCardGeneric=new WildCardGeneric<>(1.0f,2);
        wildCardGeneric=new WildCardGeneric<>(1.0f,2);
        wildCardGeneric=new WildCardGeneric<>(1.0d,2d);
        //  WildCardGeneric<? extends Number>时会报错 不指定特定类型则不报错
//        wildCardGeneric=new WildCardGeneric<>("","");

        wildCardGeneric.getX();


        List<? super Manager> list;
        list=new ArrayList<Employee>();
        //Manager满足说明super关键字是有边界的   super通配符实例内容：能存不能取
        list=new ArrayList<Manager>();
        //编译报错 CEO不满足 ? super Manager
        //list=new ArrayList<CEO>();

//        list.add(new Employee());//      编译报错 为什么呢  <? super Manager>
// 能识别 Manager()、CEO()肯定是<? super Manager>的子类 符合要求 但Employee不一定是<? super Manager>的子类
        //这也是为什么list=new ArrayList<Employee>();成立 而 list.add(new Employee());不成立的原因
        // 因为  List<? super Manager> list是不确定的  list可以等于 new ArrayList<Employee>()
        // 但list并不一定就是Employee类型的 谁知道Manager类都有哪些父类 所以去添加时自然就报错了 因为不满足 <? super Manager>
        //list.add(new Object());//同样也是不通过的 谁知道 list是个什么类型 如果是 Employee 肯定不成立的
        // 所以我们这里只能确定   Manager CEO 这种父类或者本身是Manager的一定满足<? super Manager> 其他的都不行
       /*
        List<Employee>  list02=new ArrayList<>();
        list02.add(new Object());
        */

        list.add(new Manager());
        list.add(new CEO());


        /*
         * 最后一点 如果一个泛型的类 不指定泛型类型是什么情况呢  下面的代码情形很眼熟
         * 没错就是<?>的效果
         */
        WildCardGeneric  wildCardGeneric02;
        wildCardGeneric02=new WildCardGeneric<Integer>(1,2);
//        wildCardGeneric02=new WildCardGeneric<Float>(1.0f,2);//编译报错
        wildCardGeneric02=new WildCardGeneric<>(1.0f,2);
        wildCardGeneric02=new WildCardGeneric<Float>(1.0f,2f);
        wildCardGeneric02=new WildCardGeneric<>("dd","fff");

        System.out.println(wildCardGeneric02.getX());
        System.out.println(wildCardGeneric02.getY());
        //此处编译期看不出来  运行时会报错 所以尽量不要用 <?>无界匹配 一般使用 ? extends XXX来限定一下
        Integer dd= (Integer) wildCardGeneric02.getX();
        System.out.println(wildCardGeneric02.getX());

    }


   /*

    如果你想从一个数据类型里获取数据，使用 ? extends 通配符（能取不能存）
    如果你想把对象写入一个数据结构里，使用 ? super 通配符（能存不能取）
    如果你既想存，又想取，那就别用通配符。
    */
    static class  CEO  extends Manager{

    }

    static class Manager extends Employee{

    }
    static class Employee{

    }


}
