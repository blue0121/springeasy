package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
@Slf4j
public class CronSchedule extends AbstractCron implements Schedule {

	public CronSchedule(ExecutorService executorService) {
		super(executorService);
	}

	@Override
	public void add(String id, String cron, Runnable job) {
		this.add(id, cron, job, null);
	}

	@Override
	public void add(String id, String cron, Runnable job, ExecutorService executor) {
		AssertUtil.notEmpty(id, "Schedule Id");
		AssertUtil.notEmpty(cron, "Schedule Cron");
		AssertUtil.notNull(job, "Schedule Job");
		var newExecutor = this.getExecutor(executor);
		var trigger = new CronTrigger(cron);
		var future = scheduler.schedule(() -> newExecutor.submit(job), trigger);
		futureMap.put(id, future);
		log.info("Add schedule, id: {}, cron: {}", id, cron);
	}

	private void run(ScheduleJob job, ScheduleContext ctx) {
		ctx.getExecutor().execute(() -> {
			try {
				job.run(ctx);
			} catch (Exception e) {
				log.error("ScheduleJob id: {} error,", ctx.getId(), e);
			}
		});
	}

}
