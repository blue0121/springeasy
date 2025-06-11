package io.jutil.springeasy.mybatis.mutex.exeutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-06-09
 */
@Slf4j
public class PostgreSQLExecutor extends AbstractExecutor {

	public PostgreSQLExecutor(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
	                          String table) {
		super(jdbcTemplate, transactionTemplate, table);
	}

	@Override
	public void createTable() {
		var sql = MessageFormat.format("""
                CREATE TABLE IF NOT EXISTS {0} (
                    mutex_key                   varchar(100)       primary key,
                    instance_id                 varchar(50)        not null,
                    expire_time                 timestamp(0)       not null
                )""", table);
		jdbcTemplate.update(sql);
		log.info("创建表: {}", table);
	}

	@Override
	public boolean insertKey(String key, String instanceId, LocalDateTime expireTime) {
		var sql = MessageFormat.format("""
                INSERT INTO {0} (mutex_key, instance_id, expire_time) \
                 VALUES (?, ?, ?) ON CONFLICT DO NOTHING""", table);
		var rs = jdbcTemplate.update(sql, key, instanceId, expireTime);
		return rs > 0;
	}
}
