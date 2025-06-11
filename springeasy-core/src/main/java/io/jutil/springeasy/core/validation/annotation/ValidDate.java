package io.jutil.springeasy.core.validation.annotation;

import io.jutil.springeasy.core.validation.validator.ValidDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jin Zheng
 * @since 2025-05-29
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {
	String message() default "无效的日期格式";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
