package io.jutil.springeasy.core.mutex.memory;

import io.jutil.springeasy.core.mutex.Mutex;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public class MemoryMutex implements Mutex {
	private final String key;
	private final AtomicBoolean lock;

	public MemoryMutex(String key) {
		this.key = key;
		this.lock = new AtomicBoolean(false);
	}

	@Override
	public boolean tryLock() {
		return lock.compareAndSet(false, true);
	}

	@Override
	public void unlock() {
		lock.set(false);
	}

	@Override
	public String getKey() {
		return this.key;
	}
}
