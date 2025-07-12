package io.jutil.springeasy.test.container;

import io.jutil.springeasy.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Jin Zheng
 * @since 2025-07-12
 */
class RedisTestContainerIT extends BaseRedisTest implements BaseTest {
	@Autowired
	StringRedisTemplate redisTemplate;

	@Test
	void test() {
		var key = "key";
		var value = "value";
		redisTemplate.boundValueOps(key).set(value);
		var view =  redisTemplate.boundValueOps(key).get();
		Assertions.assertEquals(value, view);

		redisTemplate.delete(key);
		Assertions.assertNull(redisTemplate.boundValueOps(key).get());
	}
}
