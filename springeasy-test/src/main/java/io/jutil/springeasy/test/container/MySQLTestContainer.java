package io.jutil.springeasy.test.container;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@Slf4j
public class MySQLTestContainer extends MySQLContainer<MySQLTestContainer> {
	private static final String DOCKER_IMAGE = "mysql:8";

	public static final MySQLTestContainer CONTAINER = new MySQLTestContainer()
			.withDatabaseName("test_db")
			.withUsername("test_username")
			.withPassword("test_password")
			.withReuse(true)
			.withLogConsumer(new Slf4jLogConsumer(log));

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(CONTAINER::destroy));
	}

	private MySQLTestContainer() {
		super(DOCKER_IMAGE);
	}

	public void destroy() {
		super.stop();
	}

	@Override
	@SuppressWarnings("java:S1186")
	public void stop() {
	}

}
