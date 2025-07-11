package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.redis.RedisTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024/7/5
 */
@ActiveProfiles("cache1")
@SpringBootTest(classes = RedisTest.Application.class)
class RedisCacheIT extends RedisTest {
	@Autowired
	RedisCacheManager cacheManager;
	RedisCache redisCache;

	final String key = "key";
	final String value = "value";

	@BeforeEach
	void beforeEach() {
		redisCache = (RedisCache) cacheManager.getCache("name");
		redisCache.clear();
	}

	@Test
	void testGet() {
		redisCache.put(key, value);
		Assertions.assertEquals(value, redisCache.get(key, String.class));

		redisCache.evict(key);
		Assertions.assertFalse(redisCache.evictIfPresent(key));
	}

	@Test
	void testGetLoader() {
		Assertions.assertEquals(value, redisCache.get(key, () -> value));
		Assertions.assertEquals(value, redisCache.get(key, () -> value));

		Assertions.assertEquals(value, redisCache.get(key, String.class));
		Assertions.assertFalse(redisCache.evictIfPresent(key));
		Assertions.assertNull(redisCache.get(key, String.class));
	}

	@Test
	void testPut() {
		var wrapper = redisCache.putIfAbsent(key, value);
		Assertions.assertNull(wrapper);

		wrapper = redisCache.putIfAbsent(key, value);
		Assertions.assertEquals(value, wrapper.get());
	}
}
