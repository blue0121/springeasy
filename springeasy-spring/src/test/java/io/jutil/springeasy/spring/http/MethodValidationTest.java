package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.validation.ValidationUtil;
import io.jutil.springeasy.spring.Application;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Jin Zheng
 * @since 2024-05-30
 */
@SpringBootTest(classes = {Application.class, MethodValidationTest.Config.class})
public class MethodValidationTest {
	@Autowired
	ValidationService service;

	@Test
	void testValidate() {
		try {
			service.test(null);
			Assertions.fail();
		} catch (ConstraintViolationException e) {
			var message = ValidationUtil.getErrorMessage(e.getConstraintViolations());
			Assertions.assertEquals("ID不能为空", message);
		}
	}

	@Configuration
	public static class Config {
		@Bean
		public ValidationService validationService() {
			return new ValidationService();
		}
	}

	@Slf4j
	@Component
	@Validated
	public static class ValidationService {
		void test(@NotNull(message = "ID不能为空") String id) {
			log.info("id: {}", id);
		}
	}
}
