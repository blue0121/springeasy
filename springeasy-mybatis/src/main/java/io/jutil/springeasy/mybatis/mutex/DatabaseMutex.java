package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.RenewSchedule;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
class DatabaseMutex implements Mutex {
	private final SqlExecutor sqlExecutor;
	private final RenewSchedule renewSchedule;
	private final String key;
	private final int expireSec;
	private final String instanceId;

	public DatabaseMutex(SqlExecutor sqlExecutor, String key,
	                     RenewSchedule renewSchedule) {
		this.sqlExecutor = sqlExecutor;
		this.key = key;
		this.renewSchedule = renewSchedule;
		this.expireSec = renewSchedule.getExpireSec();
		this.instanceId = renewSchedule.getInstanceId();
	}

	@Override
	public boolean tryLock() {
		var expireTime = DateUtil.now().plus(expireSec, ChronoUnit.SECONDS);
		var canStart = sqlExecutor.canStart(key, instanceId, expireTime);
		if (!canStart) {
			return false;
		}
		renewSchedule.startRenew(key);
		return true;
	}

	@Override
	public void unlock() {
		sqlExecutor.delete(key);
		renewSchedule.stopRenew(key);
	}

	@Override
	public String getKey() {
		return this.key;
	}

}
