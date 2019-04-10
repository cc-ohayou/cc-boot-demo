package com.cc.ccbootdemo.core.test;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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

  public static void main(String[] args) {
    int[] a={1,23,4,53,3,6,8,4,9,7};
    System.out.println(Arrays.toString(a));
    bubbleSort(a);
    System.out.println(Arrays.toString(a));
    LinkedBlockingQueue  queue=new LinkedBlockingQueue();
    queue.add(1);
    queue.add(4);
    queue.offer(2);
    queue.offer(3);
    queue.poll();
    try {
      queue.take();
      queue.put(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void bubbleSort(int[] a) {
    int len=a.length;
    for(int i=0;i<len-1;i++){
      System.out.println("第"+(i+1)+"次循环");
      for(int j=0;j<len-1-i;j++){
        if(less(a[j+1],a[j])){
          swap(a,j,j+1);
          System.out.println("i="+i+",j="+j);
        }

      }

    }

  }

  private static boolean less(int a,int b) {
    System.out.println("compare "+a+" and "+b);
    return a<b;
  }


  public static void swap(int[] a ,int j,int tmp){
    System.out.println("swp "+a[j]+" and "+a[tmp]);
       int temp=a[j];
       a[j]=a[tmp];
       a[tmp]=temp;

     }

}
