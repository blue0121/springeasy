package io.jutil.springeasy.mybatis.mutex.exeutor;

import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2025-06-08
 */
@Slf4j
public abstract class AbstractExecutor implements Executor {
	protected final JdbcTemplate jdbcTemplate;
	protected final TransactionTemplate transactionTemplate;
	protected final String table;

	protected AbstractExecutor(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
	                           String table) {
		this.jdbcTemplate = jdbcTemplate;
		this.transactionTemplate = transactionTemplate;
		this.table = table;
	}

	@Override
	public final boolean canStart(String key, String instanceId, LocalDateTime expireTime) {
		var result = transactionTemplate.execute(status -> {
			if (this.insertKey(key, instanceId, expireTime)) {
				return true;
			}
			var oldInstanceId = this.queryOldInstanceId(key, instanceId);
			if (oldInstanceId == null || oldInstanceId.isEmpty()) {
				return false;
			}
			return this.updateInstanceId(key, instanceId, oldInstanceId, expireTime);
		});
		return Boolean.TRUE.equals(result);
	}

	private String queryOldInstanceId(String key, String instanceId) {
		var query = MessageFormat.format("""
                SELECT instance_id FROM {0} WHERE mutex_key=? AND \
                 (instance_id=? OR expire_time<?)""", table);
		var now = DateUtil.now();
		var list = jdbcTemplate.queryForList(query, String.class, key, instanceId,
				Timestamp.valueOf(now));
		if (list.isEmpty()) {
			return null;
		}
		return list.getFirst();
	}

	private boolean updateInstanceId(String key, String instanceId, String oldInstanceId,
	                                 LocalDateTime expireTime) {
		var update = MessageFormat.format("""
                UPDATE {0} SET instance_id=?, expire_time=? \
                 WHERE mutex_key=? AND instance_id=?""", table);
		int rs = jdbcTemplate.update(update, instanceId, Timestamp.valueOf(expireTime),
				key, oldInstanceId);
		return rs > 0;
	}

	protected abstract boolean insertKey(String key, String instanceId, LocalDateTime expireTime);

	@Override
	public final void renew(Collection<String> keys, String instanceId, LocalDateTime expireTime) {
		if (keys == null || keys.isEmpty()) {
			return;
		}

		var sql = MessageFormat.format("""
                UPDATE {0} SET expire_time=? \
                 WHERE mutex_key IN ({1}) AND instance_id=?""",
				table, StringUtil.repeat("?", keys.size(), ","));
		List<Object> paramList = new ArrayList<>();
		paramList.add(Timestamp.valueOf(expireTime));
		paramList.addAll(keys);
		paramList.add(instanceId);
		transactionTemplate.executeWithoutResult(status -> {
			jdbcTemplate.update(sql, paramList.toArray());
		});
	}

	@Override
	public final void delete(String key) {
		var sql = MessageFormat.format("DELETE from {0} WHERE mutex_key=?", table);
		transactionTemplate.executeWithoutResult(status -> {
			jdbcTemplate.update(sql, key);
		});
	}
}
