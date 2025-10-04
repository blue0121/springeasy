package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
public abstract class SqlExecutorTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	TransactionTemplate transactionTemplate;

	SqlExecutor executor;

	final String table = "schedule_lock";
	final int second = 10;
	final String key = "key123";
	final String instanceId = UUID.randomUUID().toString();

	@BeforeEach
	public void beforeEach() {
		this.executor = new SqlExecutor(jdbcTemplate, transactionTemplate, table);
		jdbcTemplate.update("TRUNCATE TABLE " + table);
	}

	@Test
	public void testCanStart1() {
		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(key, instanceId, expire));
		this.verify(1, instanceId, expire);

		Assertions.assertTrue(executor.canStart(key, instanceId, expire));
		this.verify(1, instanceId, expire);
	}

	@Test
	public void testCanStart2() {
		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(key, "instanceId", expire));
		this.verify(1, "instanceId", expire);

		Assertions.assertFalse(executor.canStart(key, instanceId, expire));
		this.verify(1, "instanceId", expire);
	}

	@Test
	public void testCanStart3() {
		var now = DateUtil.now();
		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(key, "instanceId", expire));
		this.verify(1, "instanceId", expire);

		this.update(now.minusSeconds(second));

		Assertions.assertTrue(executor.canStart(key, instanceId, expire));
		this.verify(1, instanceId, expire);
	}

	@Test
	public void testRenew() {
		var now = DateUtil.now();
		var expire = this.getExpire();
		Assertions.assertTrue(executor.canStart(key, instanceId, now));
		this.verify(1, instanceId, now);

		executor.renew(List.of(key), instanceId, expire);
		this.verify(1, instanceId, expire);
	}

	@Test
	public void testDelete() {
		var now = DateUtil.now();
		Assertions.assertTrue(executor.canStart(key, instanceId, now));
		this.verify(1, instanceId, now);

		executor.delete(key);
		this.verify(0, instanceId, now);
	}

	private LocalDateTime getExpire() {
		return DateUtil.now().plusSeconds(second);
	}

	private void update(LocalDateTime expire) {
		var sql = MessageFormat.format("""
                UPDATE {0} SET expire_time=? WHERE mutex_key=?""", table);
		jdbcTemplate.update(sql, expire, key);
	}

	private void verify(int size, String instanceId, LocalDateTime expire) {
		var list = jdbcTemplate.queryForList("SELECT * FROM " + table);
		Assertions.assertEquals(size, list.size());
		if (size > 0) {
			var map = list.getFirst();
			Assertions.assertEquals(key, map.get("mutex_key"));
			Assertions.assertEquals(instanceId, map.get("instance_id"));
			var expireTime = (Timestamp) map.get("expire_time");
			Assertions.assertTrue(DateUtil.equal(expire, expireTime.toLocalDateTime()));
		}
	}
}
