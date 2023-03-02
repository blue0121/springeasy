package io.jutil.springeasy.core.validation;

import io.jutil.springeasy.core.validation.annotation.Phone;
import io.jutil.springeasy.core.validation.group.GetOperation;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
class PhoneTest {
	@ParameterizedTest
	@CsvSource({"13000000000,true", "020-8888888,true", "(020)8888888,true", "88888888,true", ",true",
			"1300000000a,false", "1300000000,false", "888888888,false", "a8888888,false"})
	void testValid(String text, boolean valid) {
		var obj = new TestObject(text);
		if (valid) {
			ValidationUtil.valid(obj, GetOperation.class);
		} else {
			Assertions.assertThrows(ValidationException.class,
					() -> ValidationUtil.valid(obj, GetOperation.class),
					"无效的电话号码");
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class TestObject {
		@Phone(groups = GetOperation.class)
		private String phone;
	}
}
