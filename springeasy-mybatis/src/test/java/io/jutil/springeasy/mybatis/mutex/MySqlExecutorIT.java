package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.mybatis.BaseTest;
import io.jutil.springeasy.test.container.MySQLTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
class MySqlExecutorIT implements MySQLTest, BaseTest {
	@Autowired
	SqlExecutorTest executorTest;

	@BeforeEach
	void beforeEach() {
		executorTest.beforeEach();
	}

	@Test
	void testCanStart1() {
		executorTest.testCanStart1();
	}

	@Test
	void testCanStart2() {
		executorTest.testCanStart2();
	}

	@Test
	void testCanStart3() {
		executorTest.testCanStart3();
	}

	@Test
	void testRenew() {
		executorTest.testRenew();
	}

	@Test
	void testDelete() {
		executorTest.testDelete();
	}
}
