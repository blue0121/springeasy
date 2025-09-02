package io.jutil.springeasy.mybatis.id;

import io.jutil.springeasy.mybatis.BaseTest;
import io.jutil.springeasy.spring.config.node.MachineIdContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2024-06-13
 */
class LongIdGeneratorTest implements BaseTest {

	@Test
	void testId() {
		Assertions.assertEquals(1, MachineIdContext.getMachineId());
	}
}
