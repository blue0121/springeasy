package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.util.DateUtil;
import io.jutil.springeasy.core.util.JdbcUtil;
import io.jutil.springeasy.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-02-19
 */
@Slf4j
class SqlExecutor {
	public static final int STOPPED = 0;
	public static final int RUNNING = 1;

	private final DataSource dataSource;
	private final String table;

	SqlExecutor(DataSource dataSource, String table) {
		this.dataSource = dataSource;
		this.table = table;
	}

	void createTable() {
		var sql = MessageFormat.format("""
                CREATE TABLE IF NOT EXISTS {0} (
                    job_id                      varchar(50)        primary key,
                    instance_id                 varchar(40)        not null,
                    status                      int2               not null,
                    expire_time                 timestamp(0)       not null
                )""", table);
		this.update(sql);
		log.info("Initialize table: {}", table);
	}

	void init(String jobId, String instanceId) {
		var sql = MessageFormat.format("""
                insert into {0} (job_id, instance_id, status, expire_time) \
                 values (?, ?, ?, ?)""", table);
		var now = DateUtil.now(ChronoUnit.SECONDS);
		try {
			this.update(sql, jobId, instanceId, STOPPED, Timestamp.valueOf(now));
		} catch (Exception e) {
			log.warn("Job exists, id: {}, error: {}", jobId, e.getMessage());
		}
	}

	boolean canStart(String jobId, String instanceId, LocalDateTime expireTime) {
		var query = MessageFormat.format("""
                select instance_id from {0} where job_id=? and \
                 ((instance_id=? and status=?) or expire_time<?)""", table);

		var update = MessageFormat.format("""
                update {0} set instance_id=?, expire_time=?, status=? \
                 where job_id=? and instance_id=?""", table);

		var now = DateUtil.now(ChronoUnit.SECONDS);
		var oldInstanceId = this.query(query, jobId, instanceId, STOPPED,
				Timestamp.valueOf(now));
		if (oldInstanceId == null || oldInstanceId.isEmpty()) {
			return false;
		}

		int rs = this.update(update, instanceId, Timestamp.valueOf(expireTime), RUNNING,
				jobId, oldInstanceId);
		return rs > 0;
	}

	void finish(String jobId) {
		var sql = MessageFormat.format("""
                update {0} set status=? \
                 where job_id=? and status=?""", table);
		this.update(sql, STOPPED, jobId, RUNNING);
	}

	void renew(Collection<String> jobIds, String instanceId, LocalDateTime expireTime) {
		if (jobIds == null || jobIds.isEmpty()) {
			return;
		}

		var sql = MessageFormat.format("""
                update {0} set expire_time=? \
                 where job_id in ({1}) and instance_id=?""",
				table, StringUtil.repeat("?", jobIds.size(), ","));
		List<Object> paramList = new ArrayList<>();
		paramList.add(Timestamp.valueOf(expireTime));
		paramList.addAll(jobIds);
		paramList.add(instanceId);
		this.update(sql, paramList.toArray());
	}

	void delete(String jobId) {
		var sql = MessageFormat.format("delete from {0} where job_id=?", table);
		this.update(sql, jobId);
	}

	private int update(String sql, Object...args) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			this.setArgs(statement, args);
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} finally {
			JdbcUtil.close(null, statement, conn);
		}
	}

	private String query(String sql, Object...args) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			this.setArgs(statement, args);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} finally {
			JdbcUtil.close(resultSet, statement, conn);
		}
	}

	private void setArgs(PreparedStatement statement, Object ...args) throws SQLException {
		if (args != null && args.length > 0) {
			int i = 1;
			for (var arg : args) {
				statement.setObject(i, arg);
				i++;
			}
		}
	}
}
