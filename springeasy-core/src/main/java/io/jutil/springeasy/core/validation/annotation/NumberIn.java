package io.jutil.springeasy.core.validation.annotation;

import io.jutil.springeasy.core.validation.validator.NumberInValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberInValidator.class)
public @interface NumberIn {
    long[] value();

    String message() default "无效数字值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
