package io.jutil.springeasy.internal.core.schedule;

import io.jutil.springeasy.core.schedule.Mutex;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
public class MemoryMutex implements Mutex {
	private final String id;
	private final AtomicBoolean lock;

	public MemoryMutex(String id, AtomicBoolean lock) {
		this.id = id;
		this.lock = lock;
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
	public String getId() {
		return this.id;
	}
}
