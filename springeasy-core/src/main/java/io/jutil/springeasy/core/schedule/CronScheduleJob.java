package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.schedule.impl.ScheduleContextImpl;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2024-03-01
 */
@Slf4j
public class CronScheduleJob extends AbstractCron {
	private final MutexFactory mutexFactory;

	public CronScheduleJob(ExecutorService executorService, MutexFactory mutexFactory) {
		super(executorService);
		this.mutexFactory = mutexFactory;
	}

	public void add(String id, String cron, ScheduleJob job) {
		this.add(id, cron, job, null);
	}

	public void add(String id, String cron, ScheduleJob job, ExecutorService executor) {
		AssertUtil.notEmpty(cron, "Schedule Cron");
		this.add(id, new CronTrigger(cron), job, executor);
	}

	public void add(String id, CronTrigger trigger, ScheduleJob job) {
		this.add(id, trigger, job, null);
	}

	public void add(String id, CronTrigger trigger, ScheduleJob job, ExecutorService executor) {
		AssertUtil.notEmpty(id, "Schedule Id");
		AssertUtil.notNull(trigger, "Schedule Cron");
		AssertUtil.notNull(job, "Schedule Job");
		var newExecutor = this.getExecutor(executor);
		var ctx = new ScheduleContextImpl(id, trigger.getExpression(),
				newExecutor, mutexFactory);
		var future = scheduler.schedule(() -> this.run(job, ctx), trigger);
		futureMap.put(id, future);
		log.info("Add schedule job, id: {}, cron: {}", id, trigger.getExpression());
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
