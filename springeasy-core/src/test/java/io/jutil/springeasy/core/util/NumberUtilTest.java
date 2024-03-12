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

    @CsvSource({"1024,1.00KB", "1048576,1.00MB", "1073741824,1.00GB"})
    @ParameterizedTest
    void testByteFormat(long size, String sizeName) {
        Assertions.assertEquals(sizeName, NumberUtil.byteFormat(size));
    }

    @CsvSource({"1,2", "0,0", "-1,1"})
    @ParameterizedTest
    void testZigZagEncode(int src, int dst) {
        Assertions.assertEquals(dst, NumberUtil.zigZagEncode(src));
    }

    @CsvSource({"2,1", "0,0", "1,-1"})
    @ParameterizedTest
    void testZigZagDecode(int src, int dst) {
        Assertions.assertEquals(dst, NumberUtil.zigZagDecode(src));
    }

    @CsvSource({"1,2", "0,0", "-1,1"})
    @ParameterizedTest
    void testZigZagEncode(long src, long dst) {
        Assertions.assertEquals(dst, NumberUtil.zigZagEncode(src));
    }

    @CsvSource({"2,1", "0,0", "1,-1"})
    @ParameterizedTest
    void testZigZagDecode(long src, long dst) {
        Assertions.assertEquals(dst, NumberUtil.zigZagDecode(src));
    }

    @CsvSource({"127.0.0.1,2130706433", "0.0.0.0,0", "192.168.1.1,-1062731519", "0,"})
    @ParameterizedTest
    void testFromIpv4(String ip, Integer i) {
        if (i != null) {
            Assertions.assertEquals(i, NumberUtil.fromIpv4(ip));
        } else {
            Assertions.assertThrows(IllegalArgumentException.class, () -> NumberUtil.fromIpv4(ip));
        }
    }

    @CsvSource({"java.lang.Integer,true", "int,true", "java.lang.Long,true", "long,true"})
    @ParameterizedTest
    void testIsNumber(Class<?> clazz, boolean valid) {
        Assertions.assertEquals(valid, NumberUtil.isNumber(clazz));
    }
}
