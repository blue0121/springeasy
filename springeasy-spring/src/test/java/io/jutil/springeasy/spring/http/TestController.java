package io.jutil.springeasy.spring.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Slf4j
@RestController
public class TestController {

	@PostMapping(value = "/test",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public TestResponse say(@RequestBody TestRequest request) {
		log.info("name: {}", request.getName());
		var response = new TestResponse();
		response.setName(request.getName());
		response.setMessage("Hello, " + request.getName());
		return response;
	}

}
