package io.jutil.springeasy.core.validation.annotation;

import io.jutil.springeasy.core.validation.validator.VinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VinValidator.class)
public @interface Vin {
	String message() default "无效的车架号";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
