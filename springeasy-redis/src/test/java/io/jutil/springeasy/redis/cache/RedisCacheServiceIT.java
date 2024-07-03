package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.redis.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
	@MockBean
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
		Mockito.verify(testRepository).call(Mockito.eq(key));

		view = testService.call(key);
		Assertions.assertEquals(message, view);
		Mockito.verify(testRepository).call(Mockito.eq(key));
	}

	@Test
	void testCacheEmpty() {
		var key = "key";
		var view = testService.empty(key);
		Assertions.assertNull(view);
		Mockito.verify(testRepository).call(Mockito.eq(key));

		view = testService.empty(key);
		Assertions.assertNull(view);
		Mockito.verify(testRepository).call(Mockito.eq(key));
	}

	@Configuration
	public static class Config {
		@Bean
		public TestService testService() {
			return new TestService();
		}
	}
}
