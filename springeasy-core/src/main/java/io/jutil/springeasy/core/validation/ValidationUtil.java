package io.jutil.springeasy.core.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
@Slf4j
public class ValidationUtil {
	private static Validator validator;

	private ValidationUtil() {
	}

	/**
	 * 验证对象
	 *
	 * @param object 对象
	 * @param groups 验证分组
	 */
	@SuppressWarnings({"unchecked", "java:S3740"})
	public static void valid(Object object, Class<?>... groups) throws ValidationException {
		init();

		Set set = validator.validate(object, groups);
		if (set == null || set.isEmpty()) {
			return;
		}

		throw new ValidationException(getErrorMessage(set));
	}

	public static String getErrorMessage(Set<ConstraintViolation<?>> set) {
		return set.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(","));
	}

	private static void init() {
		if (validator != null) {
			return;
		}

		synchronized (ValidationUtil.class) {
			if (validator != null) {
				return;
			}

			validator = Validation.buildDefaultValidatorFactory().getValidator();
			log.info("实例化 Validator: {}", validator.getClass().getName());
		}
	}
}
