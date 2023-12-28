package io.jutil.springeasy.core.validation;

import io.jutil.springeasy.core.validation.annotation.NumberIn;
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
class NumberInTest {
    @CsvSource({"1000,true", "2000,true", "1,false"})
    @ParameterizedTest
    void testValid(int value, boolean valid) {
        var obj = new TestObject(value);
        if (valid) {
            ValidationUtil.valid(obj, GetOperation.class);
        } else {
            Assertions.assertThrows(ValidationException.class,
                    () -> ValidationUtil.valid(obj, GetOperation.class),
                    "无效数字值");
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestObject {
        @NumberIn(value = {1000, 2000}, groups = GetOperation.class)
        private int number;
    }
}
