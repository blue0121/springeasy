package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * @author Jin Zheng
 * @since 2025-08-13
 */
class OsUtilTest {

	@EnabledOnOs(OS.WINDOWS)
	@Test
	void testIsWindows() {
		System.out.println(OsUtil.getOsName());
		Assertions.assertTrue(OsUtil.isWindows());
	}

	@DisabledOnOs(OS.WINDOWS)
	@Test
	void testIsNotWindows() {
		System.out.println(OsUtil.getOsName());
		Assertions.assertFalse(OsUtil.isWindows());
	}
}
