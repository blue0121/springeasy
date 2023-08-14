package io.jutil.springeasy.internal.core.schedule;

import io.jutil.springeasy.core.schedule.Schedule;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
@Slf4j
public class CronSchedule implements Schedule {
	private final ExecutorService executorService;
	private final TaskScheduler scheduler;
	private final ConcurrentMap<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

	public CronSchedule(ExecutorService executorService) {
		AssertUtil.notNull(executorService, "ExecutorService");
		this.executorService = executorService;
		this.scheduler = this.getTaskScheduler();
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
	}

	@Override
	public void remove(String id) {
		var future = futureMap.remove(id);
		if (future != null && !future.isCancelled()) {
			future.cancel(false);
		}
	}

	private ExecutorService getExecutor(ExecutorService executor) {
		if (executor == null) {
			return this.executorService;
		}
		return executor;
	}

	private ThreadPoolTaskScheduler getTaskScheduler() {
		var taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(1);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		taskScheduler.setAwaitTerminationSeconds(10);
		taskScheduler.initialize();
		return taskScheduler;
	}
}
