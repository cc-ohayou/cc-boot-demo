package com.cc.ccbootdemo;

import com.cc.ccbootdemo.core.common.properties.DemoProperty;
import com.cc.core.DemoPropertyCustomized;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
//@SpringBootTest(classes=CcBootDemoApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CcBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CcBootDemoApplicationTests {
    @Resource(name = "demoPropertyCustomized")
	DemoPropertyCustomized demoProperty;
	@Resource
	DemoProperty demoProperty2;
	@Test
	public void contextLoads() {
		System.out.println(demoProperty.getName()+",title="+demoProperty.getTitle());
		System.out.println(demoProperty2.getName()+",title="+demoProperty2.getTitle());
	}

}
