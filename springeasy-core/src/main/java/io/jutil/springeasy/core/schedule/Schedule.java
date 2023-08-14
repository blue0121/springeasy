package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.internal.core.schedule.CronSchedule;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface Schedule {
	static Schedule create(ExecutorService executor) {
		return new CronSchedule(executor);
	}

	void add(String id, String cron, Runnable job);

	void add(String id, String cron, Runnable job, ExecutorService executor);

	void remove(String id);

}
