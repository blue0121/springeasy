package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.Mobile;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
public class MobileValidator extends RegexValidator<Mobile> {
	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("^1\\d{10}$");
	}
}
