package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;

import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
public interface RedisMutex extends Mutex {

	void lock();

	<T> T execute(Supplier<T> f);

}
