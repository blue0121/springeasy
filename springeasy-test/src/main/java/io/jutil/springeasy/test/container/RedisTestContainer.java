package io.jutil.springeasy.test.container;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * @author Jin Zheng
 * @since 2025-04-27
 */
@Slf4j
public class RedisTestContainer {
	private static final String DOCKER_IMAGE = "redis:7-alpine";
	private static final int REDIS_PORT = 6379;

	public static final GenericContainer<?> REDIS =
			new GenericContainer<>(DOCKER_IMAGE)
					.withExposedPorts(REDIS_PORT)
					.withReuse(true)
					.waitingFor(Wait.forLogMessage(".*Ready to accept connections.*", 1))
					.withLogConsumer(new Slf4jLogConsumer(log));

	static {
		REDIS.start();
		Runtime.getRuntime().addShutdownHook(new Thread(REDIS::stop));
	}

	public static String getHost() {
		return REDIS.getHost();
	}

	public static Integer getPort() {
		return REDIS.getMappedPort(REDIS_PORT);
	}

	public static String getRedisUrl() {
		return String.format("redis://%s:%d",
				REDIS.getHost(),
				REDIS.getMappedPort(REDIS_PORT));
	}
}
