package io.jutil.springeasy.core.util;

/**
 * @author Jin Zheng
 * @since 2023-11-14
 */
public class EnumUtil {
    private EnumUtil() {
    }

    public static <T extends Enum<?>> T intToEnum(Class<T> clazz, int val) {
        for (var e : clazz.getEnumConstants()) {
            if (val == e.ordinal()) {
                return e;
            }
        }
        return null;
    }

    public static <T extends Enum<?>> T stringToEnum(Class<T> clazz, String str) {
        for (var e : clazz.getEnumConstants()) {
            if (e.name().equals(str)) {
                return e;
            }
        }
        return null;
    }
}
