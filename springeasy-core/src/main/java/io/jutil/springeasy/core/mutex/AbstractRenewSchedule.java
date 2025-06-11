package io.jutil.springeasy.core.mutex;

import io.jutil.springeasy.core.collection.ConcurrentHashSet;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
public abstract class AbstractRenewSchedule implements RenewSchedule, Runnable {
	protected final ScheduledExecutorService schedule;
	protected final int keepAliveSec;
	protected final int expireSec;
	protected final String instanceId;

	private ScheduledFuture<?> renewFuture;
	protected final Set<String> runningIdSet = new ConcurrentHashSet<>();

	protected AbstractRenewSchedule(String instanceId, int keepAliveSec, int expireSec) {
		this.instanceId = instanceId;
		this.keepAliveSec = keepAliveSec;
		this.expireSec = expireSec;
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

	@Override
	public int getKeepAliveSec() {
		return keepAliveSec;
	}

	@Override
	public int getExpireSec() {
		return expireSec;
	}

	@Override
	public String getInstanceId() {
		return instanceId;
	}
}
