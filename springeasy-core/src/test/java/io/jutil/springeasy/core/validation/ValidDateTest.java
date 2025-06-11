package io.jutil.springeasy.core.validation;

import io.jutil.springeasy.core.validation.annotation.ValidDate;
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
 * @since 2025-05-29
 */
class ValidDateTest {

	@CsvSource({"2025-05-01,true", "2025-05-01 00:00:00,false", "abc,false",
			"2025-04-31,false", "2025-02-30,false"})
	@ParameterizedTest
	void testValidDate(String text, boolean valid) {
		var obj = new TestObject(text);
		if (valid) {
			ValidationUtil.valid(obj);
		} else {
			Assertions.assertThrows(ValidationException.class,
					() -> ValidationUtil.valid(obj),
					"无效的日期格式");
		}
	}


	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class TestObject {
		@ValidDate
		private String startDate;
	}
}
