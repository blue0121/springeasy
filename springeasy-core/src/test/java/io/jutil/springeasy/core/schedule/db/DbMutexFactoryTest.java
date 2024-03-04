package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Set;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@SpringBootTest(classes = Application.class)
class DbMutexFactoryTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource dataSource;

	DbMutexFactory factory;

	final String table = "schedule_lock";
	final int second = 10;
	final String jobId = "jobId123";
	final String instanceId = UUID.randomUUID().toString();

	@BeforeEach
	void beforeEach() {
		this.factory = new DbMutexFactory(dataSource, table);
		factory.setExpireSec(second);
		factory.setKeepAliveSec(second / 5);
		factory.setInstanceId(instanceId);
		factory.setJobIdList(Set.of(jobId));
		factory.init();
		jdbcTemplate.update("truncate table " + table);
	}

	@Test
	void testGetter() {
		Assertions.assertTrue(factory.getKeepAliveSec() > 0);
		Assertions.assertTrue(factory.getExpireSec() > 0);
		Assertions.assertNotNull(factory.getJobIdList());
		Assertions.assertNotNull(factory.getSqlExecutor());
		Assertions.assertNotNull(factory.getSchedule());
	}

	@Test
	void testCreate1() {
		var m1 = factory.create(jobId);
		var m2 = factory.create(jobId);
		Assertions.assertSame(m1, m2);
	}

	@Test
	void testCreate2() {
		var m1 = factory.create("job123");
		var m2 = factory.create("job123");
		Assertions.assertNotSame(m1, m2);
	}
}
