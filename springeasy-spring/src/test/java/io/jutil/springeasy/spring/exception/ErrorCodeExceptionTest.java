package io.jutil.springeasy.spring.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-03
 */
class ErrorCodeExceptionTest {

    @Test
    void test() {
        var ex = ErrorCode.ERROR.newException();
        Assertions.assertEquals(ErrorCode.ERROR, ex.getErrorCode());
        Assertions.assertEquals(500, ex.getHttpStatus());
        Assertions.assertEquals(500000, ex.getCode());
        Assertions.assertEquals("系统错误", ex.getMessage());
        Assertions.assertEquals("{\"code\":500000,\"message\":\"系统错误\"}", ex.toJsonString());
    }

}
