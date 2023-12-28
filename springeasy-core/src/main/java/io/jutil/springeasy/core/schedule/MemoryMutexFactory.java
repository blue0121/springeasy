package io.jutil.springeasy.core.schedule;

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
