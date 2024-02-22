package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.impl.RenewSchedule;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
@Getter
@Setter
class DbMutex implements Mutex {
	private SqlExecutor sqlExecutor;
	private RenewSchedule renewSchedule;
	private String jobId;
	private int expireSec;
	private String instanceId;
	private Set<String> jobIdSet;

	@Override
	public boolean tryLock() {
		var expireTime = DateUtil.now().plus(expireSec, ChronoUnit.SECONDS);
		var canStart = sqlExecutor.canStart(jobId, instanceId, expireTime);
		if (!canStart) {
			return false;
		}
		renewSchedule.startRenew(jobId);
		return true;
	}

	@Override
	public void unlock() {
		if (this.existsInSet()) {
			sqlExecutor.finish(jobId);
		} else {
			sqlExecutor.delete(jobId);
		}
		renewSchedule.stopRenew(jobId);
	}

	@Override
	public String getId() {
		return this.jobId;
	}

	private boolean existsInSet() {
		return jobIdSet.contains(jobId);
	}

}
