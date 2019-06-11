package com.cc.ccbootdemo;

import com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy.IOrderService;
import com.cc.ccbootdemo.facade.domain.common.test.design.patterns.strategy.OrderDO;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cc.core", "com.cc.ccbootdemo","com.cc.ccbootdemo.facade.domain.common.test.design.patterns"})
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


/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类 EtfTaskApplication
		return builder.sources(CcBootDemoApplication.class);
	}*/
  @Resource
	IOrderService  orderService;
	@Bean
	public ApplicationRunner runner() {
		return args -> {

			OrderDO orderDO=new OrderDO();
			orderDO.setCode("1001");
			orderDO.setType("1");
			orderDO.setPrice(BigDecimal.ONE);
			System.out.println(orderService.handleOrder(orderDO));
		};
	}
}
