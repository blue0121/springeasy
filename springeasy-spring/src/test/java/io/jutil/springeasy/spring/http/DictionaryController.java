package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.validation.group.GetOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@Slf4j
@RestController
public class DictionaryController {
	public DictionaryController() {
	}

	@PostMapping(value = "/dictionary",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public DictionaryResponse say(@RequestBody @Validated(GetOperation.class) DictionaryRequest request) {
		log.info("name: {}", request.getName());
		var response = new DictionaryResponse();
		response.setName(request.getName());
		response.setStatus(request.getStatus());
		return response;
	}

}
