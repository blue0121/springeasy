package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.redis.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@ActiveProfiles("cache1")
@SpringBootTest(classes = {Application.class, RedisCacheServiceIT.Config.class})
class RedisCacheServiceIT {
	@Autowired
	TestService testService;

	@Autowired
	CacheManager cacheManager;

	@Test
	void testCacheManager() {
		Assertions.assertEquals(RedisCacheManager.class, cacheManager.getClass());
	}

	@Test
	void testCache() {
		Assertions.assertEquals(1, testService.add());
		Assertions.assertEquals(1, testService.add());
		Assertions.assertEquals(1, testService.add());
		Assertions.assertEquals(1, testService.getValue());

		testService.reset();
		Assertions.assertEquals(0, testService.getValue());
		Assertions.assertEquals(1, testService.add());
		Assertions.assertEquals(1, testService.add());
		Assertions.assertEquals(1, testService.getValue());

	}

	@Configuration
	public static class Config {
		@Bean
		public TestService testService() {
			return new TestService();
		}
	}
}
