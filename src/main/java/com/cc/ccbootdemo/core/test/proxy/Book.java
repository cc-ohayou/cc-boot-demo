package com.cc.ccbootdemo.core.test.proxy;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/4 14:38.
 */
@Data
public class Book implements BookInterface{

    public Book() {
    }
    private String name;
    public Book(String name) {
        this.name=name;
    }
    public void addBook() {
        System.out.println("增加图书的普通方法...");
    }
}


interface BookInterface{

     void addBook();
}