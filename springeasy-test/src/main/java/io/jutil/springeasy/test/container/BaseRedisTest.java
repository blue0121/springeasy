package io.jutil.springeasy.test.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@Slf4j
@ActiveProfiles("redis")
@Testcontainers
public abstract class BaseRedisTest {

	@DynamicPropertySource
	public static void dynamicProperty(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", RedisTestContainer::getHost);
		registry.add("spring.data.redis.port", RedisTestContainer::getPort);
		log.info(">>>>>>>>> Redis Server host: {}, port: {}",
				RedisTestContainer.getHost(), RedisTestContainer.getPort());
	}

}
