package io.jutil.springeasy.core.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
public abstract class RegexValidator<T extends Annotation> implements ConstraintValidator<T, String> {

	private Pattern pattern;

	protected abstract Pattern getValidationPattern();

	@Override
	public void initialize(T constraintAnnotation) {
		this.pattern = this.getValidationPattern();
	}

	@Override
	public boolean isValid(String text, ConstraintValidatorContext ctx) {
		if (text == null || text.isEmpty()) {
			return true;
		}

		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}
}
