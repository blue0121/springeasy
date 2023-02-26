package io.jutil.springeasy.core.validation.annotation;

import io.jutil.springeasy.internal.core.validator.MobileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jin Zheng
 * @since 2023-01-03
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {
	String message() default "无效的手机号码";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
