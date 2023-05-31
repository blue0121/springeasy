package io.jutil.springeasy.core.util;

import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
public class NumberUtil {
    public static final Pattern NUMERIC = Pattern.compile("^[-+]?[\\d]*$");

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

        return NUMERIC.matcher(str).matches();
    }

    public static int maskForInt(int length) {
        AssertUtil.isTrue(length >= 0 && length <= 31, "长度不能小于0或大于31");
        return ~(-1 << length);
    }

    public static long maskForLong(int length) {
        AssertUtil.isTrue(length >= 0 && length <= 63, "长度不能小于0或大于63");
        return ~(-1L << length);
    }

}
