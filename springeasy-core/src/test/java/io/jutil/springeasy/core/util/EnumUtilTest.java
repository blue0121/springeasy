package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
class EnumUtilTest {

    @Test
    void testIntToEnum() {
        Assertions.assertEquals(Status.ACTIVE, EnumUtil.intToEnum(Status.class, 0));
        Assertions.assertEquals(Status.INACTIVE, EnumUtil.intToEnum(Status.class, 1));
        Assertions.assertNull(EnumUtil.intToEnum(Status.class, 2));
    }

    @Test
    void testStringToEnum() {
        Assertions.assertEquals(Status.ACTIVE, EnumUtil.stringToEnum(Status.class, "ACTIVE"));
        Assertions.assertEquals(Status.INACTIVE, EnumUtil.stringToEnum(Status.class, "INACTIVE"));
        Assertions.assertNull(EnumUtil.stringToEnum(Status.class, "abc"));
    }

    enum Status {
        ACTIVE,
        INACTIVE,
    }
}
