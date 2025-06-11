package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Jin Zheng
 * @since 2025-05-29
 */
public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		try {
			var date = LocalDate.parse(value, FORMATTER);
			return value.equals(date.toString());
		} catch (DateTimeParseException ex) {
			return false;
		}
	}
}
