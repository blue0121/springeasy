package io.jutil.springeasy.springmvc.config;

import io.jutil.springeasy.core.util.JsonUtil;
import io.jutil.springeasy.springmvc.BaseTest;
import io.jutil.springeasy.springmvc.controller.TestRequest;
import io.jutil.springeasy.springmvc.controller.TestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-01-02-19:42
 */
public class FastJsonTest extends BaseTest {

	@BeforeEach
	public void beforeEach() {
		this.init();
	}

	@Test
	public void testString() {
		var url = this.buildUrl("/test/blue");
		var response = httpTemplate.get(url, Map.of("Accept", "text/plain"));
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals("Hello, blue", response.getBody());
	}

	@Test
	public void testJson() {
		var url = this.buildUrl("/test");
		var request = new TestRequest("blue");
		var response = httpTemplate.post(url, JsonUtil.output(request));
		Assertions.assertEquals(200, response.getStatusCode());

		var body = response.convertTo(TestResponse.class);
		Assertions.assertEquals("blue", body.getName());
		Assertions.assertEquals("Hello, blue", body.getMessage());
	}
}
