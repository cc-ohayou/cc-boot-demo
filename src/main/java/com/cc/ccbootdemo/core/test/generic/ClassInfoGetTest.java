package com.cc.ccbootdemo.core.test.generic;

import com.cc.ccbootdemo.facade.domain.common.util.MyClassUtil;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/11 15:17.
 */
public class ClassInfoGetTest<Integer> extends generalClass implements  Info,InfoGeneric<Integer>{

    public static void main(String[] args) {
        Type type= ClassInfoGetTest.class.getGenericSuperclass();
        ////获取普通接口的方法 直接继承
        Type[] types2= ClassInfoGetTest.class.getInterfaces();
        //获取泛型接口的方法
        Type[] types3= ClassInfoGetTest.class.getGenericInterfaces();
        System.out.println(type);
        System.out.println("直接继承的普通接口："+Arrays.toString(types2));
        System.out.println("直接继承的泛型接口："+Arrays.toString(types3));
        System.out.println("所有继承的接口(包含所有父类的接口)："+ Arrays.toString(MyClassUtil.getAllInterface(ClassInfoGetTest.class)));

        try {
            Class  cl=Class.forName("com.cc.ccbootdemo.core.test.generic.ClassInfoGetTest");

           /* Class.forName(String className)不仅会将类加载进来，而且会对其进行初始化，
           而ClassLoader.loadClass(String ClassName)则只是将类加载进来，
           而没有对类进行初始化。static代码块不会执行 初始静态变量值也不会初始化
           一般来讲，他们两个是通用的，但如果你加载类依赖初始化值的话，
           那ClassLoader.loadClass(String ClassName)将不再适用
           举例来说:
           在JDBC编程中，常看到这样的用法，Class.forName(“com.mysql.jdbc.Driver”)，
           如果换成了getClass().getClassLoader().loadClass(“com.mysql.jdbc.Driver”)，就不行

           */
            int modifiers=    cl.getModifiers();
            System.out.println("是否是接口："+ Modifier.isInterface(modifiers));
            System.out.println("是否是final："+ Modifier.isFinal(modifiers));
            System.out.println("修饰符："+ Modifier.toString(modifiers));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}





class  generalClass implements InfoSuper{

}

class  genericClass<T>{

}
 interface Info extends InfoSuper{
}

interface InfoSuper {
}
interface InfoGeneric<T> {
}