package io.jutil.springeasy.mybatis.test;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer> {
	private static final String DOCKER_IMAGE = "postgres:17-alpine";
	private static final String DATABASE = "testdb";
	private static final String USERNAME = "testuser";
	private static final String PASSWORD = "testpass";

	public static final PostgreSQLTestContainer POSTGRESQL_CONTAINER = new PostgreSQLTestContainer()
			.withDatabaseName(DATABASE)
			.withUsername(USERNAME)
			.withPassword(PASSWORD)
			.withReuse(true);

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(POSTGRESQL_CONTAINER::destroy));
	}

	private PostgreSQLTestContainer() {
		super(DOCKER_IMAGE);
	}

	public void destroy() {
		super.stop();
	}

	@Override
	public void stop() {
	}

}
