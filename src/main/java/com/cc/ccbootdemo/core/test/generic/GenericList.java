package com.cc.ccbootdemo.core.test.generic;

import com.cc.ccbootdemo.core.test.QueueTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/29 15:53.
 */
public class GenericList<Y> {



    public void testReentrantGeneric(List<Y> list){
        Point p=new Point<List<Y>>();

        p.setX(list);
        System.out.println(p.getX().toString() );
    }

    public static void main(String[] args) {
        List<QueueTest> queueTests=new ArrayList<>();
        queueTests.add(new QueueTest("test01"));
        new GenericList<QueueTest>().testReentrantGeneric(queueTests);
    }
}
