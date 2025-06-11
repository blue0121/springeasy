package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.mybatis.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
class DatabaseMutexFactoryTest extends BaseTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	DataSource dataSource;

	DatabaseMutexFactory factory;

	final String table = "schedule_lock";
	final int second = 10;
	final String key = "key123";
	String instanceId;

	@BeforeEach
	void beforeEach() {
		this.factory = new DatabaseMutexFactory(jdbcTemplate, transactionTemplate, table,
				second / 5, second);
		this.instanceId = factory.getInstanceId();
		jdbcTemplate.update("TRUNCATE TABLE " + table);
	}

	@Test
	void testGetter() {
		Assertions.assertTrue(factory.getKeepAliveSec() > 0);
		Assertions.assertTrue(factory.getExpireSec() > 0);
		Assertions.assertNotNull(factory.getSqlExecutor());
		Assertions.assertNotNull(factory.getSchedule());
	}

	@Test
	void testCreate1() {
		var m1 = factory.create(key);
		var m2 = factory.create(key);
		Assertions.assertNotSame(m1, m2);
	}

	@Test
	void testCreate2() {
		var m1 = factory.create("job123");
		var m2 = factory.create("job123");
		Assertions.assertNotSame(m1, m2);
	}
}
