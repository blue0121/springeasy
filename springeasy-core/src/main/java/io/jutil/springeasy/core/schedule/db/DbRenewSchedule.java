package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.collection.ConcurrentHashSet;
import io.jutil.springeasy.core.schedule.impl.RenewSchedule;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
class DbRenewSchedule implements RenewSchedule, Runnable {
	private final SqlExecutor sqlExecutor;
	private final ScheduledExecutorService schedule;
	private final Set<String> runningIdSet = new ConcurrentHashSet<>();
	private final int keepAliveSec;
	private final int expireSec;
	private final String instanceId;

	private ScheduledFuture<?> renewFuture;

	DbRenewSchedule(SqlExecutor sqlExecutor, String instanceId,
	                int keepAliveSec, int expireSec) {
		this.sqlExecutor = sqlExecutor;
		this.keepAliveSec = keepAliveSec;
		this.expireSec = expireSec;
		this.instanceId = instanceId;
		this.schedule = new ScheduledThreadPoolExecutor(1);
	}

	@Override
	public void startRenew(String jobId) {
		runningIdSet.add(jobId);
		if (renewFuture != null) {
			return;
		}
		synchronized (this) {
			if (renewFuture != null) {
				return;
			}
			renewFuture = schedule.scheduleAtFixedRate(this, keepAliveSec,
					keepAliveSec, TimeUnit.SECONDS);
		}
	}

	@Override
	public void run() {
		var expireTime = DateUtil.now(ChronoUnit.SECONDS).plusSeconds(expireSec);
		sqlExecutor.renew(runningIdSet, instanceId, expireTime);
		if (log.isDebugEnabled()) {
			log.debug("Schedule renew, jobId: {}, expireTime: {}",
					runningIdSet, expireTime);
		}
	}

	@Override
	public void stopRenew(String jobId) {
		runningIdSet.remove(jobId);
		if (!runningIdSet.isEmpty()) {
			return;
		}
		if (renewFuture == null) {
			return;
		}
		synchronized (this) {
			if (renewFuture == null) {
				return;
			}
			renewFuture.cancel(false);
			renewFuture = null;
		}
	}

	public void shutdown() {
		schedule.shutdown();
	}

}
