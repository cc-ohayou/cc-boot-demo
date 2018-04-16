package com.cc.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/16/016 21:11.
 */
@Component
public class DemoPropertyCustomized {
    @Value("${com.ccbootdemo.name}")
    private  String name;
    @Value("${com.ccbootdemo.title}")
    private  String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
