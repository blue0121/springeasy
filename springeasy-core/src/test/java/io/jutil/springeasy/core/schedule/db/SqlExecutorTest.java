package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.Application;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@SpringBootTest(classes = Application.class)
class SqlExecutorTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource dataSource;

	SqlExecutor executor;

	final String table = "schedule_lock";
	final int second = 10;
	final String jobId = "jobId123";
	final String instanceId = UUID.randomUUID().toString();

	@BeforeEach
	void beforeEach() {
		this.executor = new SqlExecutor(dataSource, table);
		this.executor.createTable();
		jdbcTemplate.update("truncate table " + table);
	}

	@Test
	void testInit() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);

		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);
	}

	@Test
	void testFinish() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);

		executor.finish(jobId);
		this.verify(1, SqlExecutor.STOPPED, now);

		this.update(SqlExecutor.RUNNING, now);
		this.verify(1, SqlExecutor.RUNNING, now);

		executor.finish(jobId);
		this.verify(1, SqlExecutor.STOPPED, now);
	}

	@Test
	void testCanStart1() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);

		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(jobId, instanceId, expire));
		this.verify(1, SqlExecutor.RUNNING, expire);
	}

	@Test
	void testCanStart2() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, "instanceId");

		this.update(SqlExecutor.STOPPED, now.minusSeconds(second));

		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(jobId, instanceId, expire));
		this.verify(1, SqlExecutor.RUNNING, expire);
	}

	@Test
	void testCanStart3() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, "instanceId");

		this.update(SqlExecutor.RUNNING, now.minusSeconds(second));

		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(jobId, instanceId, expire));
		this.verify(1, SqlExecutor.RUNNING, expire);
	}

	@Test
	void testCanStart4() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);

		var expire = this.getExpire();
		Assertions.assertFalse(executor.canStart(jobId, "instanceId", expire));
		this.verify(1, SqlExecutor.STOPPED, now);
	}

	@Test
	void testRenew() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);

		this.update(SqlExecutor.RUNNING, now);
		this.verify(1, SqlExecutor.RUNNING, now);

		var expire = this.getExpire();
		executor.renew(List.of(jobId), instanceId, expire);
		this.verify(1, SqlExecutor.RUNNING, expire);
	}

	@Test
	void testDelete() {
		var now = DateUtil.now(ChronoUnit.SECONDS);
		executor.init(jobId, instanceId);
		this.verify(1, SqlExecutor.STOPPED, now);

		executor.delete(jobId);
		this.verify(0, SqlExecutor.STOPPED, now);
	}

	private LocalDateTime getExpire() {
		return DateUtil.now(ChronoUnit.SECONDS).plusSeconds(second);
	}

	private void update(int status, LocalDateTime expire) {
		var sql = MessageFormat.format("""
                update {0} set status=?, expire_time=? where job_id=?""", table);
		jdbcTemplate.update(sql, status, expire, jobId);
	}

	private void verify(int size, int status, LocalDateTime expire) {
		var list = jdbcTemplate.queryForList("select * from " + table);
		Assertions.assertEquals(size, list.size());
		if (size > 0) {
			var map = list.get(0);
			Assertions.assertEquals(jobId, map.get("job_id"));
			Assertions.assertEquals(instanceId, map.get("instance_id"));
			Assertions.assertEquals(status, map.get("status"));
			var time = (Timestamp) map.get("expire_time");
			Assertions.assertTrue(DateUtil.equal(expire, time.toLocalDateTime()));
		}
	}
}
