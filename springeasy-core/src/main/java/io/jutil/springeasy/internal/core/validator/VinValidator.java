package io.jutil.springeasy.internal.core.validator;


import io.jutil.springeasy.core.validation.annotation.Vin;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
public class VinValidator extends RegexValidator<Vin> {

	@Override
	protected Pattern getValidationPattern() {
		return Pattern.compile("^[A-Z0-9]{17}$");
	}
}
