package com.cc.ccbootdemo.core.common.config;

import com.cc.ccbootdemo.core.common.properties.DemoProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.LockSupport;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/4/16/016 22:16.
 */
@Configuration
public class DemoConfig {
   /* @Bean(name="demoProperty")
    public DemoProperty DemoProperty(){
        return new DemoProperty();
    }*/


    public static void main(String[] args) throws Exception {
        short s1=1;
        short s2= (short) (s1+((short)1));
        float f1=0.1f;
        float f2=0.1f+1;
        double d=f2+0.03;
        float f3= (float) (0.1f+d);
        float f4= (float)d+0.03f;
        a3();
    }


    public static void a3() throws Exception {

        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread...");
                LockSupport.park(this);
                System.out.println("thread done.");
            }
        });

        t.start();
        System.out.println("main thread done.");

        Thread.sleep(2000);
        LockSupport.unpark(t);

//        t.interrupt(); //如果因为park而被阻塞，可以响应中断请求，并且不会抛出InterruptedException。

    }



}
