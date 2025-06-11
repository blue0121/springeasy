package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.mutex.AbstractRenewSchedule;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
class DatabaseRenewSchedule extends AbstractRenewSchedule {
	private final SqlExecutor sqlExecutor;

	DatabaseRenewSchedule(SqlExecutor sqlExecutor, String instanceId,
	                      int keepAliveSec, int expireSec) {
		super(instanceId, keepAliveSec, expireSec);
		this.sqlExecutor = sqlExecutor;
	}

	@Override
	public void run() {
		var expireTime = DateUtil.now(ChronoUnit.SECONDS).plusSeconds(expireSec);
		sqlExecutor.renew(runningIdSet, instanceId, expireTime);
		if (log.isDebugEnabled()) {
			log.debug("Schedule renew, jobId: {}, expireTime: {}", runningIdSet, expireTime);
		}
	}

}
