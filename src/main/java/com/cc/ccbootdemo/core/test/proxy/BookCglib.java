package com.cc.ccbootdemo.core.test.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/4 14:38.
 */
public class BookCglib implements MethodInterceptor{

    private Object target;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy(Object target ){
        this.target=target;
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("事务开始");
        methodProxy.invokeSuper(o,args);
        System.out.println("事务结束");

        return null;
    }

    public static void main(String[] args) {
        BookCglib cglib=new BookCglib();
        Object o=cglib.getProxy(new Book());
        Book bookCglib=(Book)o;
        bookCglib.addBook();
    }
}
