package io.jutil.springeasy.mybatis.test;

import org.testcontainers.containers.MySQLContainer;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
public class MySQLTestContainer extends MySQLContainer<MySQLTestContainer> {
	private static final String DOCKER_IMAGE = "mysql:8";
	private static final String DATABASE = "testdb";
	private static final String USERNAME = "testuser";
	private static final String PASSWORD = "testpass";

	public static final MySQLTestContainer MYSQL_CONTAINER = new MySQLTestContainer()
			.withDatabaseName(DATABASE)
			.withUsername(USERNAME)
			.withPassword(PASSWORD)
			.withReuse(true);

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(MYSQL_CONTAINER::destroy));
	}

	private MySQLTestContainer() {
		super(DOCKER_IMAGE);
	}

	public void destroy() {
		super.stop();
	}

	@Override
	public void stop() {
	}

}
