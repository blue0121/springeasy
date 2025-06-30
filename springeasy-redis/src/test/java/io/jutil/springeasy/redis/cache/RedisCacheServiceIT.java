package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.redis.RedisTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@ActiveProfiles("cache1")
@SpringBootTest(classes = {RedisTest.Application.class, RedisCacheServiceIT.Config.class})
class RedisCacheServiceIT extends RedisTest {
	@Autowired
	TestService testService;
	@MockitoBean
	TestRepository testRepository;

	@Autowired
	CacheManager cacheManager;

	@BeforeEach
	void beforeEach() {
		testService.reset();
	}

	@Test
	void testCacheManager() {
		Assertions.assertEquals(RedisCacheManager.class, cacheManager.getClass());
	}

	@Test
	void testCache() {
		var key = "key";
		var message = "call, key: key";
		var view = testService.call(key);
		Assertions.assertEquals(message, view);
		Mockito.verify(testRepository).call(key);

		view = testService.call(key);
		Assertions.assertEquals(message, view);
		Mockito.verify(testRepository).call(key);
	}

	@Test
	void testCacheEmpty() {
		var key = "key";
		var view = testService.empty(key);
		Assertions.assertNull(view);
		Mockito.verify(testRepository).call(key);

		view = testService.empty(key);
		Assertions.assertNull(view);
		Mockito.verify(testRepository).call(key);
	}

	@Configuration
	public static class Config {
		@Bean
		public TestService testService() {
			return new TestService();
		}
	}
}
