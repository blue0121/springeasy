package io.jutil.springeasy.core.validation;

import io.jutil.springeasy.core.validation.annotation.StringIn;
import io.jutil.springeasy.core.validation.group.GetOperation;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2023-11-15
 */
class StringInTest {
    @CsvSource({"text1,true", "text2,true", "abc,false"})
    @ParameterizedTest
    void testValid(String value, boolean valid) {
        var obj = new TestObject(value);
        if (valid) {
            ValidationUtil.valid(obj, GetOperation.class);
        } else {
            Assertions.assertThrows(ValidationException.class,
                    () -> ValidationUtil.valid(obj, GetOperation.class),
                    "无效字符串值");
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestObject {
        @StringIn(value = {"text1", "text2"}, groups = GetOperation.class)
        private String text;
    }
}
