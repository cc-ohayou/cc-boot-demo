package com.cc.ccbootdemo.core.test;

import lombok.Data;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/9/7 14:18.
 */
@Data
public class QueueTest {
  private String name;

  public QueueTest(String name) {
    this.name = name;
  }
}
