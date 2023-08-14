package io.jutil.springeasy.internal.core.schedule;

import io.jutil.springeasy.core.schedule.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
@Slf4j
public class CronSchedule implements Schedule {
	private final TaskScheduler scheduler;

	public CronSchedule(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void add(String id, String cron, Runnable job) {

	}

	@Override
	public void remove(String id) {

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
