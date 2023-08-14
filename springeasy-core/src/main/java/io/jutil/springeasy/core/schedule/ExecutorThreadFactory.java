package io.jutil.springeasy.core.schedule;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
public class ExecutorThreadFactory implements ThreadFactory {
	private final String prefix;
	private final AtomicInteger counter = new AtomicInteger(1);

	public ExecutorThreadFactory(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public Thread newThread(Runnable r) {
		var thread = new Thread(r);
		thread.setName(this.getThreadName());
		return thread;
	}

	private String getThreadName() {
		var index = counter.getAndIncrement();
		return prefix + "-" + index;
	}
}
