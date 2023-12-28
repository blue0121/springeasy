package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.Vin;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
public class VinValidator extends RegexValidator<Vin> {

	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("^[A-Z0-9]{17}$");
	}
}
