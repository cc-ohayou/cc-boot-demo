package com.cc.ccbootdemo;

import com.cc.ccbootdemo.core.common.properties.DemoProperty;
import com.cc.ccbootdemo.web.controller.CcTestController;
import com.cc.ccbootdemo.web.controller.HelloController;
import com.cc.core.DemoPropertyCustomized;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@SpringBootTest(classes=CcBootDemoApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CcBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CcBootDemoApplicationTests {
    @Resource(name = "demoPropertyCustomized")
	DemoPropertyCustomized demoProperty;
	@Resource
	DemoProperty demoProperty2;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	private MockMvc ccMvc;

	 /* mvc.perform(MockMvcRequestBuilders.get("/data/getMarkers")
			  .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("lat", "123.123").param("lon", "456.456")
                .accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));*/
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
		ccMvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种

	}

	@Test
	public void contextLoads() {
		System.out.println(",randomValue="+demoProperty2.getValue());
		/*ExecutorService ex= Executors.newFixedThreadPool(3);
		ExecutorService ex2= Executors.newCachedThreadPool();
		ExecutorService ex3= Executors.newSingleThreadExecutor();*/
	}
	@Test
	public void testHelloControler() throws Exception {
		// 测试UserController
		RequestBuilder request = null;

		// 1、get查一下user列表，应该为空
		request = get("/hello/say");
		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello World cc")));
		System.err.println(content().string(equalTo("Hello World cc")));
		System.err.println(equalTo("Hello World cc"));

	}
	@Test
	public void testCCControler() throws Exception {
		// 测试UserController
		RequestBuilder request = null;

		// 1、get查一下user列表，应该为空
//		request = get("/cc/city/list");
		MockHttpServletResponse response=ccMvc.perform(MockMvcRequestBuilders.get("/cc/city/list")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.param("cityName", "杭州"))
//				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse();
		ServletOutputStream stream=response.getOutputStream();

//		byte[] bufferArray=new byte[1024*1024];

		   stream.println();
		   stream.close();
	}

}
