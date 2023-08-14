package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.internal.core.schedule.MemoryMutexFactory;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public interface MutexFactory {

	static MutexFactory createForMemory() {
		return new MemoryMutexFactory();
	}

	Mutex create(String id);

}
