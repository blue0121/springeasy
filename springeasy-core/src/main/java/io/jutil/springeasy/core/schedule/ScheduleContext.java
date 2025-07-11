package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.mutex.MutexFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
public interface ScheduleContext {

	String getId();

	String getCron();

	ExecutorService getExecutor();

	MutexFactory getMutexFactory();

}
