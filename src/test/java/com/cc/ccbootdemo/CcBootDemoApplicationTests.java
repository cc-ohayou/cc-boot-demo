package com.cc.ccbootdemo;

import com.cc.ccbootdemo.core.common.properties.BaseProperties;
import com.cc.ccbootdemo.core.common.properties.DemoProperty;
import com.cc.ccbootdemo.core.common.properties.resource.BaseResourceProperties;
import com.cc.ccbootdemo.core.manager.RedisManager;
import com.cc.ccbootdemo.core.service.MailService;
import com.cc.ccbootdemo.core.service.UserService;
import com.cc.ccbootdemo.facade.domain.common.dataobject.mail.MailInfo;
import com.cc.ccbootdemo.facade.domain.common.util.RandomStringUtil;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;

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
	@Resource
	BaseResourceProperties baseResourceProperties;
	@Resource
	BaseProperties baseProperties;

    @Resource
	MailService mailService;
	@Resource
	UserService userService;
	private MockMvc mvc;
	private MockMvc ccMvc;
    @Resource
	RedisManager redisManager;
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

	}
	@Test
	public void testResourceInject(){
		System.out.println(baseResourceProperties);
		System.out.println(baseResourceProperties);
		System.out.println(System.getenv("CC_RESOURCE_DIR"));

	}
    @Test
	public void mailTest(){
		String text=" MailTest.java * Created on 2008-1-23 下午04:56:38 * Description: Test for mail sending */ package cn.edu.ccnu.inc.test; import java.util.Properties; import org.springframework.mail.MailSender; import org.springframework.mail.SimpleMailMessage; import org.springframework.mail.javamail.JavaMailSenderImpl; import junit.framework.TestCase; /** * @author <a href=\"mailto:aliyunzixun@xxx.com\">Iven</a> */ public class MailTest extends TestCase { private static MailSender sender = null; private static SimpleMailMessage message = null; public void setUp() { sender = new JavaMailSenderImpl(); ((JavaMailSenderImpl)sender).setHost(\"smtp.163.com\"); ((JavaMailSenderImpl)sender).setUsername(\"username\"); ((JavaMailSenderImpl)sender).setPassword(\"password\"); Properties config = new Properties(); config.put(\"mail.smtp.auth\", \"true\"); ((JavaMailSenderImpl)sender).setJavaMailProperties(config); message = new SimpleMailMessage(); } public void testSend() { message.setTo(\"aliyunzixun@xxx.com\"); message.setSubject(\"Test my owen sending program\"); message.setFrom(\"aliyunzixun@xxx.com\"); message.setText(\"Test......\"); this.assertNotNull(sender); sender.send(message); } }";
		MailInfo  info=new MailInfo();
		//13758080693@163.com
		String[] toMailAddresses={"ohayousekai@sina.com","840794748@qq.com"};
		String verifyCode=RandomStringUtil.generateString(6);
		info.setContent("您此次的验证码是："+ verifyCode);
		info.setFrom("ohayousekai@sina.com");
		info.setTo(toMailAddresses);
		info.setSubject("您此次的验证码是："+ verifyCode);

		try {
			mailService.sendMail(info);
//			redisManager.
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void initOperBizList(){

		try {
			userService.initOperList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
