package com.cc.ccbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cc.core", "com.cc.ccbootdemo"})
//@MapperScan(basePackages = "com.cc.ccbootdemo.core.mapper")
public class CcBootDemoApplication {



	public static void main(String[] args) {
		SpringApplication.run(CcBootDemoApplication.class, args);

	}
}
