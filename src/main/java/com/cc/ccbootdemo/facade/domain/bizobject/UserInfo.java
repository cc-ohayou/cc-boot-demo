package com.cc.ccbootdemo.facade.domain.bizobject;

import com.cc.ccbootdemo.facade.domain.dataobject.UserAttachDO;
import lombok.Data;

import java.util.HashSet;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/1/14 10:24.
 */
@Data
public class UserInfo extends UserAttachDO{
    /**
     * 昵称 可允许特殊符号 但要防止sql注入 可以允许表情
     */
    private String uid;
    private String userName;
    private String pwd;
    private String nickName;
    private String description;
    private String phone;
    /**
     * 邮箱不可为空  用于找回密码
     */
    private String mail;
    private String headImage;
    private String createTime;
    private String updateTime;
    private String sid;
    private String salty;



}



class Singleton{
    private  static Singleton instance;

    public static Singleton getInstance(){

        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null){
                   instance=new Singleton();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
        HashSet s=new HashSet<>();
//        s.add()

        for (int i = 0; i <10 ; i++) {
            new Thread(){
                @Override
                public void run() {
                    System.out.println(Singleton.getInstance());
                }
            }.run();
        }

    }
}
