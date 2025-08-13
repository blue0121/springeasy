package io.jutil.springeasy.core.util;

/**
 * @author Jin Zheng
 * @since 2025-08-13
 */
public class OsUtil {
	private static final String OS_NAME = System.getProperty("os.name");

	private OsUtil() {
	}

	public static String getOsName() {
		return OS_NAME;
	}

	public static boolean isWindows() {
		return OS_NAME.contains("Windows");
	}
}
