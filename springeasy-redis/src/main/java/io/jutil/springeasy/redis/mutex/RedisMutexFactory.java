package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.core.mutex.MutexType;
import io.jutil.springeasy.core.mutex.RenewSchedule;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
@Getter
public class RedisMutexFactory implements MutexFactory, DisposableBean {
	private final StringRedisTemplate redisTemplate;
	private final String rootKey;

	private final int keepAliveSec;
	private final int expireSec;
	private final String instanceId;
	private final RenewSchedule schedule;

	public RedisMutexFactory(StringRedisTemplate redisTemplate, String rootKey,
	                         int keepAliveSec, int expireSec) {
		AssertUtil.notNull(redisTemplate, "redisTemplate");
		AssertUtil.notEmpty(rootKey, "rootKey");
		AssertUtil.nonNegative(keepAliveSec, "KeepAliveSecond");
		AssertUtil.nonNegative(expireSec, "ExpireSecond");
		this.redisTemplate = redisTemplate;
		this.rootKey = rootKey;
		this.keepAliveSec = keepAliveSec;
		this.expireSec = expireSec;
		this.instanceId = UUID.randomUUID().toString();
		this.schedule = new RedisRenewSchedule(redisTemplate, instanceId,
				keepAliveSec, expireSec);
	}

	@Override
	public MutexType getType() {
		return MutexType.REDIS;
	}

	@Override
	public Mutex create(String id) {
		log.info("Create RedisMutex, id: {}", id);
		return new RedisMutexImpl(redisTemplate, id, schedule);
	}

	@Override
	public void destroy() {
		if (schedule != null) {
			schedule.shutdown();
		}
	}
}
