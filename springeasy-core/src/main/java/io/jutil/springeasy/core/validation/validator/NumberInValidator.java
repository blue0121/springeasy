package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.NumberIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
public class NumberInValidator implements ConstraintValidator<NumberIn, Number> {
    private long[] array;

    @Override
    public void initialize(NumberIn annotation) {
        this.array = annotation.value();
    }

    @Override
    public boolean isValid(Number num, ConstraintValidatorContext ctx) {
        if (num == null) {
            return true;
        }

        for (var e : array) {
            if (num.longValue() == e) {
                return true;
            }
        }
        return false;
    }
}
