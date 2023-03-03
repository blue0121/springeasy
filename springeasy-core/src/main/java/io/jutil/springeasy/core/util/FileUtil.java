package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-02-07
 */
@Slf4j
public class FileUtil {
    private static final String KEY_CLASSPATH = "classpath:";

    private FileUtil() {
    }

    public static boolean isClassPath(String path) {
        return path.startsWith(KEY_CLASSPATH);
    }

    public static String extractClassPath(String path) {
        return path.substring(KEY_CLASSPATH.length());
    }

}
