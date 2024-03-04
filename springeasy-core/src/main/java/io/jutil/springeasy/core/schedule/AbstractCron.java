package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.util.AssertUtil;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Jin Zheng
 * @since 2024-03-01
 */
public abstract class AbstractCron {
	protected final ExecutorService executorService;
	protected final TaskScheduler scheduler;
	protected final ConcurrentMap<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

	protected AbstractCron(ExecutorService executorService) {
		AssertUtil.notNull(executorService, "ExecutorService");
		this.executorService = executorService;
		this.scheduler = this.getTaskScheduler();
	}

	public void remove(String id) {
		var future = futureMap.remove(id);
		if (future != null && !future.isCancelled()) {
			future.cancel(false);
		}
	}

	protected ExecutorService getExecutor(ExecutorService executor) {
		if (executor == null) {
			return this.executorService;
		}
		return executor;
	}

	protected ThreadPoolTaskScheduler getTaskScheduler() {
		var taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(1);
		taskScheduler.setRemoveOnCancelPolicy(true);
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		taskScheduler.setAwaitTerminationSeconds(10);
		taskScheduler.initialize();
		return taskScheduler;
	}
}
