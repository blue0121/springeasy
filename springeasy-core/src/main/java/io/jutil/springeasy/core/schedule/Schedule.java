package io.jutil.springeasy.core.schedule;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface Schedule {

	void add(String id, String cron, Runnable job);

	void add(String id, String cron, Runnable job, ExecutorService executor);

	void remove(String id);

}
