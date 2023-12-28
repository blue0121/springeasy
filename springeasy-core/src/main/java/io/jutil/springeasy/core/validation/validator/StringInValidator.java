package io.jutil.springeasy.core.validation.validator;

import io.jutil.springeasy.core.validation.annotation.StringIn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
public class StringInValidator implements ConstraintValidator<StringIn, String> {
    private final Set<String> strSet = new HashSet<>();

    @Override
    public void initialize(StringIn annotation) {
        for (var str : annotation.value()) {
            strSet.add(str);
        }
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext ctx) {
        if (text == null || text.isEmpty()) {
            return true;
        }

        return strSet.contains(text);
    }
}
