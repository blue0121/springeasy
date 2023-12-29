package io.jutil.springeasy.core.validation.annotation;

import io.jutil.springeasy.core.validation.validator.EnumInValidator;
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
@Constraint(validatedBy = EnumInValidator.class)
@SuppressWarnings("java:S1452")
public @interface EnumIn {
    Class<? extends Enum<?>> value();

    String message() default "无效枚举值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
