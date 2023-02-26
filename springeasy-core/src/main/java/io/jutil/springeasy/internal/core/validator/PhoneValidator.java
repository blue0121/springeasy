package io.jutil.springeasy.internal.core.validator;

import io.jutil.springeasy.core.validation.annotation.Phone;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
public class PhoneValidator extends RegexValidator<Phone> {
	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("(^[0-9]{3,4}\\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\\([0-9]{3,4}\\)[0-9]{3,8}$)|(^0{0,1}1\\d{10}$)");
	}
}
