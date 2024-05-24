package io.jutil.springeasy.redis.mutex;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
class RedisMutexImpl implements RedisMutex {
	private final RLock rLock;
	private final String key;

	public RedisMutexImpl(RLock rLock, String key) {
		this.rLock = rLock;
		this.key = key;
	}

	@Override
	public void lock(long leaseTime, TimeUnit unit) {
		rLock.lock(leaseTime, unit);
	}

	@Override
	public <T> T execute(long leaseTime, TimeUnit unit, Supplier<T> f) {
		T result = null;
		try {
			rLock.lock(leaseTime, unit);
			log.info("Redis lock, key: {}, {} {}", key, leaseTime, unit);
			result = f.get();
		} finally {
			rLock.unlock();
		}
		return result;
	}

	@Override
	public boolean tryLock() {
		return rLock.tryLock();
	}

	@Override
	public void unlock() {
		rLock.unlock();
	}

	@Override
	public String getId() {
		return this.key;
	}
}
