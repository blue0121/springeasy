package io.jutil.springeasy.core.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

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
	public static void valid(Object object, Class<?>... groups) throws ValidationException {
		init();

		Set<ConstraintViolation<Object>> set = validator.validate(object, groups);
		if (set == null || set.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<Object> cv : set) {
			sb.append(cv.getMessage()).append(",");
		}
		if (sb.length() > 1) {
			sb.delete(sb.length() - 1, sb.length());
		}

		throw new ValidationException(sb.toString());
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
