package com.qunhe.toilet;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author bupo
 * @DATE 2020/8/20 16:49
 * @Description 
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.qunhe.toilet","com.qunhe.toilet.facade.domain.common.test.design"})
public class QunheIntelligentToiletApplication {


	public static void main(String[] args) {
		SpringApplication.run(QunheIntelligentToiletApplication.class, args);

	}




}
