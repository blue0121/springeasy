package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class NumberUtilTest {
	public NumberUtilTest() {
	}

    @Test
    public void testIsInteger() {
        Assertions.assertTrue(NumberUtil.isInteger("-1"));
        Assertions.assertTrue(NumberUtil.isInteger("0"));
        Assertions.assertTrue(NumberUtil.isInteger("1"));
        Assertions.assertTrue(NumberUtil.isInteger("100"));
        Assertions.assertTrue(NumberUtil.isInteger("+100"));

        Assertions.assertFalse(NumberUtil.isInteger("1.1"));
        Assertions.assertFalse(NumberUtil.isInteger("a"));
        Assertions.assertFalse(NumberUtil.isInteger("中"));
    }

}
