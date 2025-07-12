package io.jutil.springeasy.core.mutex.memory;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.core.mutex.MutexType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
@Slf4j
public class MemoryMutexFactory implements MutexFactory {

	public MemoryMutexFactory() {
		log.info("Initialize MemoryMutexFactory");
	}

	@Override
	public MutexType getType() {
		return MutexType.MEMORY;
	}

	@Override
	public Mutex create(String key) {
		return new MemoryMutex(key);
	}

	@Override
	@SuppressWarnings("java:S1186")
	public void destroy() {

	}
}
