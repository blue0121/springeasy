package io.jutil.springeasy.core.util;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class NumberUtil {
    public static final Pattern INTEGER = Pattern.compile("^[-+]?[\\d]*$");

    private NumberUtil() {
    }

    /**
     * 判断是否为整型数字字符串
     *
     * @param str 字符串
     * @return true为整型数字字符串
     */
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        return INTEGER.matcher(str).matches();
    }

    public static int maskForInt(int length) {
        AssertUtil.isTrue(length >= 0 && length <= 31, "长度不能小于0或大于31");
        return ~(-1 << length);
    }

    public static long maskForLong(int length) {
        AssertUtil.isTrue(length >= 0 && length <= 63, "长度不能小于0或大于63");
        return ~(-1L << length);
    }

    public static String byteFormat(long size) {
        String[] a = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        double val = size;
        int pos = 0;
        while (val >= 1024.0d && pos < a.length) {
            val /= 1024;
            pos++;
        }
        return String.format("%.2f%s", val, a[pos]);
    }

    public static int zigZagEncode(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static long zigZagEncode(long l) {
        return (l >> 63) ^ (l << 1);
    }

    public static int zigZagDecode(int i) {
        return ((i >>> 1) ^ -(i & 1));
    }

    public static long zigZagDecode(long l) {
        return ((l >>> 1) ^ -(l & 1));
    }

}
