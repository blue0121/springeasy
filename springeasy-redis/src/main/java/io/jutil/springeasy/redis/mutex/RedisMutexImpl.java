package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.RenewSchedule;
import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
class RedisMutexImpl implements RedisMutex {
	private final StringRedisTemplate redisTemplate;
	private final String key;
	private final RenewSchedule renewSchedule;
	private final int keepAliveSec;
	private final int expireSec;
	private final String instanceId;
	private final RedisScript<Long> script;

	public RedisMutexImpl(StringRedisTemplate redisTemplate, String key,
	                      RenewSchedule renewSchedule) {
		this.redisTemplate = redisTemplate;
		this.key = key;
		this.renewSchedule = renewSchedule;
		this.keepAliveSec = renewSchedule.getKeepAliveSec();
		this.expireSec = renewSchedule.getExpireSec();
		this.instanceId = renewSchedule.getInstanceId();
		this.script = RedisScript.of("""
				if redis.call('get', KEYS[1]) == ARGV[1] then
				   return redis.call('del', KEYS[1])
				else
				   return 0
				end""", Long.class);
	}

	@Override
	public void lock() {
		long waitMillis = expireSec * 1000L;
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < waitMillis) {
			if (!this.tryLock()) {
				return;
			}
			WaitUtil.sleep(keepAliveSec, TimeUnit.SECONDS);
		}
	}

	@Override
	public <T> T execute(Supplier<T> f) {
		T result = null;
		try {
			this.lock();
			log.info("Redis mutex lock, key: {}", key);
			result = f.get();
		} finally {
			this.unlock();
		}
		return result;
	}

	@Override
	public boolean tryLock() {
		var success = redisTemplate.opsForValue().setIfAbsent(key, instanceId,
				expireSec, TimeUnit.SECONDS);
		if (!Boolean.TRUE.equals(success)) {
			return false;
		}
		renewSchedule.startRenew(key);
		return true;
	}

	@Override
	public void unlock() {
		redisTemplate.execute(script, List.of(key), instanceId);
	}

	@Override
	public String getKey() {
		return this.key;
	}
}
