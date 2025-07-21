package io.jutil.springeasy.spring.config;

import io.jutil.springeasy.core.codec.json.Json;
import io.jutil.springeasy.spring.BaseTest;
import io.jutil.springeasy.spring.http.TestRequest;
import io.jutil.springeasy.spring.http.TestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-01-02-19:42
 */
class FastJsonTest extends BaseTest {

	@BeforeEach
	void beforeEach() {
		this.init();
	}

	@Test
	void testString() {
		var url = this.buildUrl("/test/blue");
		var response = httpTemplate.get(url, Map.of("Accept", "text/plain"));
		Assertions.assertEquals(200, response.getStatusCode());
		Assertions.assertEquals("Hello, blue", response.getBody());
	}

	@Test
	void testJson() {
		var url = this.buildUrl("/test");
		var request = new TestRequest("blue");
		var response = httpTemplate.post(url, Json.output(request));
		Assertions.assertEquals(200, response.getStatusCode());

		var body = response.convertTo(TestResponse.class);
		Assertions.assertEquals("blue", body.getName());
		Assertions.assertEquals("Hello, blue", body.getMessage());
	}
}
