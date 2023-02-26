package io.jutil.springeasy.internal.core.validator;

import io.jutil.springeasy.core.validation.annotation.Mobile;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
public class MobileValidator extends RegexValidator<Mobile> {
	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("^1\\d{10}$");
	}
}
