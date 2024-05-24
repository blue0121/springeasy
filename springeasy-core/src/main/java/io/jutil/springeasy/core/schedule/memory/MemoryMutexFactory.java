package io.jutil.springeasy.core.schedule.memory;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.MutexFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public class MemoryMutexFactory implements MutexFactory {

	@Override
	public String getType() {
		return "memory";
	}

	@Override
	public Mutex create(String id) {
		var lock = new AtomicBoolean(false);
		return new MemoryMutex(id, lock);
	}
}
