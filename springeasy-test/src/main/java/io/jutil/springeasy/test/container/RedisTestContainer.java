package io.jutil.springeasy.test.container;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-04-27
 */
@Slf4j
public class RedisTestContainer {
	private static final String DOCKER_IMAGE = "redis:8.4.0-alpine";
	private static final int REDIS_PORT = 6379;

	public static final GenericContainer<?> CONTAINER =
			new GenericContainer<>(DOCKER_IMAGE)
					.withExposedPorts(REDIS_PORT)
					.withReuse(true)
					.waitingFor(Wait.forLogMessage(".*Ready to accept connections.*", 1))
					.withLogConsumer(new Slf4jLogConsumer(log));

	static {
		CONTAINER.start();
		Runtime.getRuntime().addShutdownHook(new Thread(CONTAINER::stop));
	}

	private RedisTestContainer() {}

	public static String getHost() {
		return CONTAINER.getHost();
	}

	public static Integer getPort() {
		return CONTAINER.getMappedPort(REDIS_PORT);
	}

	public static Map<String, String> getProperties() {
		Map<String, String> map = new HashMap<>();
		map.put("spring.data.redis.host", RedisTestContainer.getHost());
		map.put("spring.data.redis.port", String.valueOf(RedisTestContainer.getPort()));
		log.info(">>>>>>>>> Redis Server host: {}, port: {}",
				RedisTestContainer.getHost(), RedisTestContainer.getPort());
		return map;
	}

}
