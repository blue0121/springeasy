package io.jutil.springeasy.core.schedule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Slf4j
public abstract class SingleScheduleJob implements ScheduleJob {

	@Override
	public final void run(ScheduleContext ctx) {
		var mutex = ctx.getMutexFactory().create(ctx.getId());
		if (!mutex.tryLock()) {
			log.warn("Schedule [{}] is running", mutex.getKey());
			return;
		}
		log.info(">> Schedule [{}] start", mutex.getKey());
		try {
			this.runInternal(ctx);
		} finally {
			mutex.unlock();
			log.info("<< Schedule [{}] stop", mutex.getKey());
		}
	}

	protected abstract void runInternal(ScheduleContext ctx);

}
