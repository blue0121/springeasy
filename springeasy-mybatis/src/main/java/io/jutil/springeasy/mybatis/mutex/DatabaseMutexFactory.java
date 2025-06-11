package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.core.mutex.MutexType;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
@Getter
public class DatabaseMutexFactory implements MutexFactory {
	private final int keepAliveSec;
	private final int expireSec;
	private final String instanceId;

	private final SqlExecutor sqlExecutor;
	private final DatabaseRenewSchedule schedule;
	private final Map<String, Mutex> mutexMap = new HashMap<>();

	public DatabaseMutexFactory(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
	                            String table, int keepAliveSec, int expireSec) {
		AssertUtil.nonNegative(keepAliveSec, "KeepAliveSecond");
		AssertUtil.nonNegative(expireSec, "ExpireSecond");

		this.sqlExecutor = new SqlExecutor(jdbcTemplate, transactionTemplate, table);
		this.instanceId = UUID.randomUUID().toString();
		this.keepAliveSec = keepAliveSec;
		this.expireSec = expireSec;

		this.schedule = new DatabaseRenewSchedule(sqlExecutor, instanceId,
				keepAliveSec, expireSec);

		log.info("初始化 DatabaseMutexFactory, table: {}, instanceId: {}", table, instanceId);
	}

	@Override
	public MutexType getType() {
		return MutexType.DATABASE;
	}

	@Override
	public Mutex create(String key) {
		var m = mutexMap.get(key);
		if (m != null) {
			return m;
		}

		return new DatabaseMutex(sqlExecutor, key, schedule);
	}

	@Override
	public void destroy() {
		schedule.shutdown();
	}

}
