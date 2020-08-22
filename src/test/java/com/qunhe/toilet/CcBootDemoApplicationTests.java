package com.qunhe.toilet;

import com.qunhe.toilet.core.ImplServiceTaskCondition;
import com.qunhe.toilet.core.manager.RedisManager;
import com.qunhe.toilet.web.controller.HelloController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QunheIntelligentToiletApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CcBootDemoApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Resource
    RedisManager redisManager;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testHelloControler() throws Exception {
        // 测试UserController
        RequestBuilder request = null;
        request = get("/hello/say");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World cc")));
        System.err.println(content().string(equalTo("Hello World cc")));
        System.err.println(equalTo("Hello World cc"));
    }

    @Test
    public void testMapepr() {
        ImplServiceTaskCondition condition = new ImplServiceTaskCondition();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 0);
        condition.setTaskStates(list);
    }

}
