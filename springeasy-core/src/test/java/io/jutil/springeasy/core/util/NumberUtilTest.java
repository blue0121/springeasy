package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
class NumberUtilTest {
	public NumberUtilTest() {
	}

    @Test
    void testIsInteger() {
        Assertions.assertTrue(NumberUtil.isInteger("-1"));
        Assertions.assertTrue(NumberUtil.isInteger("0"));
        Assertions.assertTrue(NumberUtil.isInteger("1"));
        Assertions.assertTrue(NumberUtil.isInteger("100"));
        Assertions.assertTrue(NumberUtil.isInteger("+100"));

        Assertions.assertFalse(NumberUtil.isInteger("1.1"));
        Assertions.assertFalse(NumberUtil.isInteger("a"));
        Assertions.assertFalse(NumberUtil.isInteger("中"));
    }

    @CsvSource({"0", "1", "10", "31"})
    @ParameterizedTest
    void testMaskForInt(int length) {
        var mask = NumberUtil.maskForInt(length);
        System.out.println(Integer.toBinaryString(mask));
        int i = 0;
        while (i < length) {
            Assertions.assertEquals(1, (mask >>> i) & 1, "第 " + i + " 位不为1");
            i++;
        }
        while (i < 32) {
            Assertions.assertEquals(0, (mask >>> i) & 1, "第 " + i + " 位不为0");
            i++;
        }
    }

    @CsvSource({"0", "1", "10", "31", "50", "63"})
    @ParameterizedTest
    void testMaskForLong(int length) {
        var mask = NumberUtil.maskForLong(length);
        System.out.println(Long.toBinaryString(mask));
        int i = 0;
        while (i < length) {
            Assertions.assertEquals(1, (mask >>> i) & 1, "第 " + i + " 位不为1");
            i++;
        }
        while (i < 64) {
            Assertions.assertEquals(0, (mask >>> i) & 1, "第 " + i + " 位不为0");
            i++;
        }
    }

}
