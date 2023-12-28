package io.jutil.springeasy.core.validation;

import io.jutil.springeasy.core.validation.annotation.EnumIn;
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
 * @since 2023-11-14
 */
class EnumInTest {

    @CsvSource({"0,true", "1,true", "2,false"})
    @ParameterizedTest
    void testNumberValid(int value, boolean valid) {
        var obj = new TestNumberObject(value);
        if (valid) {
            ValidationUtil.valid(obj, GetOperation.class);
        } else {
            Assertions.assertThrows(ValidationException.class,
                    () -> ValidationUtil.valid(obj, GetOperation.class),
                    "无效枚举值");
        }
    }


    @CsvSource({"ACTIVE,true", "INACTIVE,true", "abc,false"})
    @ParameterizedTest
    void testStringValid(String value, boolean valid) {
        var obj = new TestStringObject(value);
        if (valid) {
            ValidationUtil.valid(obj, GetOperation.class);
        } else {
            Assertions.assertThrows(ValidationException.class,
                    () -> ValidationUtil.valid(obj, GetOperation.class),
                    "无效枚举值");
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestNumberObject {
        @EnumIn(value = Status.class, groups = GetOperation.class)
        private int status;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestStringObject {
        @EnumIn(value = Status.class, groups = GetOperation.class)
        private String status;
    }

    enum Status {
        ACTIVE,
        INACTIVE,
    }
}
