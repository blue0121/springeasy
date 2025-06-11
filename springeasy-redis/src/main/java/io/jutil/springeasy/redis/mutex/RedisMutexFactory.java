package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.core.mutex.MutexType;
import io.jutil.springeasy.core.mutex.RenewSchedule;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
@Getter
@Setter
public class RedisMutexFactory implements MutexFactory, InitializingBean, DisposableBean {
	private final StringRedisTemplate redisTemplate;
	private final String rootKey;

	private int keepAliveSec;
	private int expireSec;
	private String instanceId;
	private RenewSchedule schedule;

	public RedisMutexFactory(StringRedisTemplate redisTemplate, String rootKey) {
		this.redisTemplate = redisTemplate;
		this.rootKey = rootKey;
		this.instanceId = UUID.randomUUID().toString();
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

	@Override
	public void afterPropertiesSet() {
		AssertUtil.nonNegative(keepAliveSec, "KeepAliveSecond");
		AssertUtil.nonNegative(expireSec, "ExpireSecond");
		AssertUtil.notEmpty(instanceId, "Instance Id");
		this.schedule = new RedisRenewSchedule(redisTemplate, instanceId,
				keepAliveSec, expireSec);
	}
}
