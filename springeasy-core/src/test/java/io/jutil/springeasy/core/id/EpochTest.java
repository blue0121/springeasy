package io.jutil.springeasy.core.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2025-01-11
 */
class EpochTest {

	@Test
	void test() {
		var options = new EpochOptions();
		var epoch = options.getEpochMillis();
		Assertions.assertTrue(epoch > 0);
		System.out.println(epoch);
	}
}
