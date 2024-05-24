package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Getter
@Setter
public class DbMutexFactory implements MutexFactory {
	private int keepAliveSec;
	private int expireSec;
	private String instanceId;
	private Set<String> jobIdList;

	private final SqlExecutor sqlExecutor;
	private DbRenewSchedule schedule;
	private final Map<String, Mutex> mutexMap = new HashMap<>();

	public DbMutexFactory(DataSource dataSource, String table) {
		this.sqlExecutor = new SqlExecutor(dataSource, table);
		this.sqlExecutor.createTable();
		this.instanceId = UUID.randomUUID().toString();
	}

	@Override
	public String getType() {
		return "db";
	}

	@Override
	public Mutex create(String jobId) {
		var m = mutexMap.get(jobId);
		if (m != null) {
			return m;
		}

		return this.createMutex(jobId);
	}

	private Mutex createMutex(String jobId) {
		var mutex = new DbMutex();
		mutex.setSqlExecutor(sqlExecutor);
		mutex.setRenewSchedule(schedule);
		mutex.setInstanceId(instanceId);
		mutex.setExpireSec(expireSec);
		mutex.setJobIdSet(jobIdList);
		mutex.setJobId(jobId);
		sqlExecutor.init(jobId, instanceId);
		return mutex;
	}

	public void init() {
		AssertUtil.nonNegative(keepAliveSec, "KeepAliveSecond");
		AssertUtil.nonNegative(expireSec, "ExpireSecond");
		AssertUtil.notEmpty(jobIdList, "Job Id");
		AssertUtil.notEmpty(instanceId, "Instance Id");
		this.schedule = new DbRenewSchedule(sqlExecutor, instanceId,
				keepAliveSec, expireSec);

		for (var jobId : jobIdList) {
			var mutex = this.createMutex(jobId);
			mutexMap.put(jobId, mutex);
		}
	}

	public void destroy() {
		schedule.shutdown();
	}

}
