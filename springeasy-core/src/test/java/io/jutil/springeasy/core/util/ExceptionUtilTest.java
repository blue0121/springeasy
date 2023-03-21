package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-03
 */
class ExceptionUtilTest {

    @Test
    void testExceptionToString() {
        var ex = new RuntimeException("exe");
        var str = ExceptionUtil.exceptionToString(ex);
        System.out.println(str);
        Assertions.assertNotNull(str);
    }

}
