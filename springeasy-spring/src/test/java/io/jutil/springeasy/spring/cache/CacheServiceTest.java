package io.jutil.springeasy.spring.cache;

import io.jutil.springeasy.spring.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@ActiveProfiles("cache")
@SpringBootTest(classes = {Application.class, CacheServiceTest.Config.class})
class CacheServiceTest {
	@Autowired
	TestService testService;

	@Autowired
	CacheManager cacheManager;

	@Test
	void testCacheManager() {
		Assertions.assertEquals(CaffeineCacheManager.class, cacheManager.getClass());
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

	@Getter
	@NoArgsConstructor
	@CacheConfig(cacheNames = "test1")
	public static class TestService {
		private int value = 0;

		@Cacheable
		public int add() {
			return ++value;
		}

		@CacheEvict
		public void reset() {
			this.value = 0;
		}
	}
}
