package io.jutil.springeasy.spring.http;

import io.jutil.springeasy.core.validation.ValidationUtil;
import io.jutil.springeasy.spring.Application;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2024-05-30
 */
@SpringBootTest(classes = {Application.class})
class MethodValidationTest {
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
}
