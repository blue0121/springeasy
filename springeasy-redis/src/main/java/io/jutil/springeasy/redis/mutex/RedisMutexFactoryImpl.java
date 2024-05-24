package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.MutexFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
public class RedisMutexFactoryImpl implements MutexFactory {
	private final RedissonClient client;

	public RedisMutexFactoryImpl(RedissonClient client) {
		this.client = client;
	}

	@Override
	public String getType() {
		return "redis";
	}

	@Override
	public Mutex create(String id) {
		var rLock = client.getLock(id);
		log.info("Create RedisMutex, id: {}", rLock.getName());
		return new RedisMutexImpl(rLock, id);
	}
}
