package io.jutil.springeasy.core.schedule;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface Schedule {

	void add(String id, String cron, Runnable job);

	void remove(String id);

}
