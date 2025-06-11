package io.jutil.springeasy.redis;

import io.jutil.springeasy.redis.test.RedisTestContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-04-27
 */
@Slf4j
@Testcontainers
@SpringBootTest(classes = RedisTest.Application.class)
public abstract class RedisTest {

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", RedisTestContainer::getHost);
		registry.add("spring.data.redis.port", RedisTestContainer::getPort);
		log.info(">>>>>>>>> Redis Server host: {}, port: {}",
				RedisTestContainer.getHost(), RedisTestContainer.getPort());

		var server = RedisTestContainer.getRedisUrl();
		registry.add("springeasy.redis.server", () -> server);
		log.info(">>>>>>>>> Redis Server URL: {}", server);
	}

	@SpringBootApplication
	public static class Application {
	}
}
