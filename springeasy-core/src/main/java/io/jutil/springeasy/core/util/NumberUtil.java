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

}
