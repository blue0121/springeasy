package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.EnumIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
public class EnumInValidator implements ConstraintValidator<EnumIn, Object> {
    private final Set<Integer> intSet = new HashSet<>();
    private final Set<String> strSet = new HashSet<>();

    @Override
    public void initialize(EnumIn annotation) {
        for (var e : annotation.value().getEnumConstants()) {
            intSet.add(e.ordinal());
            strSet.add(e.name());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (value == null) {
            return true;
        }

        if (value instanceof CharSequence str) {
            return strSet.contains(str);
        }
        if (value instanceof Number num) {
            return intSet.contains(num.intValue());
        }
        return false;
    }
}
