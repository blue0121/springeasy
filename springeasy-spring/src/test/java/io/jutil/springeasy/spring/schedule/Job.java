package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.schedule.ScheduleContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2025-04-21
 */
@Slf4j
public class Job extends SpringSingleScheduleJob {
	boolean flag = false;

	@Override
	protected void runInternal(ScheduleContext ctx) {
		log.info("Run SpringSingleScheduleJob, id: {}, cron: {}", ctx.getId(), ctx.getCron());
		flag = true;
	}

	@Override
	public String getId() {
		return "job";
	}
}
