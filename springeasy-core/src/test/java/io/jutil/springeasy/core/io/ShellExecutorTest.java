package io.jutil.springeasy.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * @author Jin Zheng
 * @since 2024/7/13
 */
class ShellExecutorTest {

	@Test
	@DisabledOnOs(OS.WINDOWS)
	void testExecute() {
		var start = System.currentTimeMillis();
		var cmd = "ls";
		var rs = ShellExecutor.execute(cmd);
		System.out.println("used time: " + (System.currentTimeMillis() - start));
		Assertions.assertEquals(0, rs.getExitValue());
		Assertions.assertNull(rs.getCause());
		System.out.println(rs.getOutput());
	}

	@Test
	@DisabledOnOs(OS.WINDOWS)
	void testMultiExecute() {
		var start = System.currentTimeMillis();
		var cmd = "ls /";
		for (int i = 0; i < 5; i++) {
			var rs = ShellExecutor.execute(cmd);
			Assertions.assertEquals(0, rs.getExitValue());
			Assertions.assertNull(rs.getCause());
		}
		System.out.println("used time: " + (System.currentTimeMillis() - start));
	}
}
