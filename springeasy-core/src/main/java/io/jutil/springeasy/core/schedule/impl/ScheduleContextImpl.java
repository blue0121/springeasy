package io.jutil.springeasy.core.schedule.impl;

import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.core.schedule.ScheduleContext;
import lombok.Getter;

import java.util.concurrent.ExecutorService;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@Getter
public class ScheduleContextImpl implements ScheduleContext {
	private final String id;
	private final String cron;
	private final ExecutorService executor;
	private final MutexFactory mutexFactory;

	public ScheduleContextImpl(String id, String cron, ExecutorService executor,
	                           MutexFactory mutexFactory) {
		this.id = id;
		this.cron = cron;
		this.executor = executor;
		this.mutexFactory = mutexFactory;
	}

}
