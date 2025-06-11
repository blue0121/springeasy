package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.AbstractRenewSchedule;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
@Slf4j
class RedisRenewSchedule extends AbstractRenewSchedule {
	private final StringRedisTemplate redisTemplate;

	public RedisRenewSchedule(StringRedisTemplate redisTemplate, String instanceId,
	                          int keepAliveSec, int expireSec) {
		super(instanceId, keepAliveSec, expireSec);
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run() {
		for (var key : runningIdSet) {
			redisTemplate.expire(key, expireSec, TimeUnit.SECONDS);
		}
		if (log.isDebugEnabled()) {
			var expireTime = DateUtil.now(ChronoUnit.SECONDS).plusSeconds(expireSec);
			log.debug("Redis schedule renew, key: {}, expireTime: {}",
					runningIdSet, expireTime);
		}
	}

}
