package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.Phone;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
public class PhoneValidator extends RegexValidator<Phone> {
	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("(^\\d{3,4}-\\d{7,8}$)|(^\\d{7,8}$)|(^\\(\\d{3,4}\\)\\d{3,8}$)|(^0?1\\d{10}$)");
	}
}
