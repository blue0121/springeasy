package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.redis.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024/7/5
 */
@ActiveProfiles("cache1")
@SpringBootTest(classes = Application.class)
class RedisCacheIT {
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

		Assertions.assertTrue(redisCache.evictIfPresent(key));
	}

	@Test
	void testPut() {
		var wrapper = redisCache.putIfAbsent(key, value);
		Assertions.assertNull(wrapper);

		wrapper = redisCache.putIfAbsent(key, value);
		Assertions.assertEquals(value, wrapper.get());
	}
}
