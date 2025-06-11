package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.mybatis.metadata.MetadataUtil;
import io.jutil.springeasy.mybatis.mutex.exeutor.Executor;
import io.jutil.springeasy.mybatis.mutex.exeutor.H2Executor;
import io.jutil.springeasy.mybatis.mutex.exeutor.MySQLExecutor;
import io.jutil.springeasy.mybatis.mutex.exeutor.PostgreSQLExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Jin Zheng
 * @since 2024-02-19
 */
@Slf4j
class SqlExecutor {
	private final Executor executor;

	SqlExecutor(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
	            String table) {
		AssertUtil.notNull(jdbcTemplate, "JdbcTemplate");
		AssertUtil.notNull(transactionTemplate, "TransactionTemplate");
		AssertUtil.notEmpty(table, "Table");
		var dataSource = jdbcTemplate.getDataSource();
		var info = MetadataUtil.getDatabaseInfo(dataSource);
		this.executor = switch (info.product()) {
			case H2 -> new H2Executor(jdbcTemplate, transactionTemplate, table);
			case MYSQL -> new MySQLExecutor(jdbcTemplate, transactionTemplate, table);
			case POSTGRESQL -> new PostgreSQLExecutor(jdbcTemplate, transactionTemplate, table);
		};
		executor.createTable();
	}

	boolean canStart(String key, String instanceId, LocalDateTime expireTime) {
		return executor.canStart(key, instanceId, expireTime);
	}

	void renew(Collection<String> keys, String instanceId, LocalDateTime expireTime) {
		executor.renew(keys, instanceId, expireTime);
	}

	void delete(String key) {
		executor.delete(key);
	}

}
