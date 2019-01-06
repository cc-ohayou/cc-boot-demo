package com.cc.ccbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cc.core", "com.cc.ccbootdemo"})
//@MapperScan(basePackages = "com.cc.ccbootdemo.core.mapper")
//extends SpringBootServletInitializer
public class CcBootDemoApplication {
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CcBootDemoApplication.class);
	}*/


//kallen_common_334a85aed93a6b81b14c9b830b396c2b.unity3d
	//kallen_c3_jk_a1e54e772907864a453493e1938f0863.unity3d

	//kallen_common_676a777f689e50acd3fddc8f83d01d01.unity3d
	//kallen_c3_jk_be462ecff6634ebf3c0854a613b06a6a.unity3d
	public static void main(String[] args) {
		SpringApplication.run(CcBootDemoApplication.class, args);

	}
}
