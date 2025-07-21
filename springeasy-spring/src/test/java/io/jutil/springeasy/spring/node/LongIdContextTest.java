package io.jutil.springeasy.spring.node;

import io.jutil.springeasy.spring.Application;
import io.jutil.springeasy.spring.config.node.LongIdContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2025-07-16
 */
@ActiveProfiles("node")
@SpringBootTest(classes = Application.class)
class LongIdContextTest {

	@Test
	void testLongId() {
		var id = LongIdContext.longId();
		System.out.println(id);
		Assertions.assertNotNull(id);
		Assertions.assertTrue(id > 0);
	}
}
