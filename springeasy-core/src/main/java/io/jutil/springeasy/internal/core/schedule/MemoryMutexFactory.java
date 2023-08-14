package io.jutil.springeasy.internal.core.schedule;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.MutexFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public class MemoryMutexFactory implements MutexFactory {

	@Override
	public Mutex create(String id) {
		var lock = new AtomicBoolean(false);
		return new MemoryMutex(id, lock);
	}
}
