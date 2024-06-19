package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.validation.group.GetOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin Zheng
 * @since 2022-12-16
 */
@Slf4j
@RestController
public class TestController {
	@Autowired
	ValidationService validationService;

	@GetMapping("/test/{name}")
	public String say(@PathVariable("name") String name) {
		log.info("name: {}", name);
		return "Hello, " + name;
	}

	@PostMapping(value = "/test",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public TestResponse say(@RequestBody @Validated(GetOperation.class) TestRequest request) {
		log.info("name: {}", request.getName());
		var response = new TestResponse();
		response.setName(request.getName());
		response.setMessage("Hello, " + request.getName());
		return response;
	}

	@GetMapping("/validate")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void validate(@RequestParam(value = "id", required = false) String id) {
		validationService.test(id);
	}

}
