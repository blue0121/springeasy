package io.jutil.springeasy.spring.http;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Jin Zheng
 * @since 2024-06-19
 */
@Slf4j
@Component
@Validated
public class ValidationService {

	public void test(@NotNull(message = "ID不能为空") String id) {
		log.info("id: {}", id);
	}
}
