package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.schedule.Mutex;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
public interface RedisMutex extends Mutex {

	void lock(long leaseTime, TimeUnit unit);

	<T> T execute(long leaseTime, TimeUnit unit, Supplier<T> f);

}
