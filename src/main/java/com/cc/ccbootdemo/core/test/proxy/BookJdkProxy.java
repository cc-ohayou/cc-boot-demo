package com.cc.ccbootdemo.core.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/4 15:05.
 */
public class BookJdkProxy implements InvocationHandler {

   private Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk proxy before");
        method.invoke(target,args);
        System.out.println("jdk proxy after");
        return null;
    }


    //定义获取代理对象方法
    private   Object getProxy(Object targetObject){
        //为目标对象target赋值
        this.target = targetObject;
        //JDK动态代理只能针对实现了接口的类进行代理，newProxyInstance 函数所需参数就可看出
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }


    public static void main(String[] args) {
        Object book=new BookJdkProxy().getProxy(new Book("hahaha"));
        ((BookInterface)book).addBook();
    }
}
