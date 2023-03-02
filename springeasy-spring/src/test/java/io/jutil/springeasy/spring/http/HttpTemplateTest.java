package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.http.AsyncHttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.util.JsonUtil;
import io.jutil.springeasy.spring.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2022-12-23
 */
@ActiveProfiles("http")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpTemplateTest {
    @LocalServerPort
    int port;

	@Autowired
    @Qualifier("test1")
    HttpTemplate httpTemplate;

    @Autowired
    @Qualifier("test2")
    AsyncHttpTemplate asyncHttpTemplate;

    @Test
    void testPost() {
        var url = "http://localhost:" + port + "/test";
        var request = new TestRequest("blue");
        var response = httpTemplate.post(url, JsonUtil.output(request));
        Assertions.assertEquals(200, response.getStatusCode());
        var entity = response.convertTo(TestResponse.class);
        Assertions.assertEquals("blue", entity.getName());
        Assertions.assertEquals("Hello, blue", entity.getMessage());
    }

    @Test
    void testPostAsync() throws Exception {
        var url = "http://localhost:" + port + "/test";
        var request = new TestRequest("red");
        var response = asyncHttpTemplate.post(url, JsonUtil.output(request)).get();
        Assertions.assertEquals(200, response.getStatusCode());
        var entity = response.convertTo(TestResponse.class);
        Assertions.assertEquals("red", entity.getName());
        Assertions.assertEquals("Hello, red", entity.getMessage());
    }
}
