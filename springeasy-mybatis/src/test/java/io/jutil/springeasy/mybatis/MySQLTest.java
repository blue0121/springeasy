package io.jutil.springeasy.mybatis;

import io.jutil.springeasy.mybatis.test.MySQLTestContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@ActiveProfiles("mysql")
@Testcontainers
public abstract class MySQLTest extends BaseTest {

	@Container
	protected static final MySQLTestContainer MYSQL_CONTAINER = MySQLTestContainer.MYSQL_CONTAINER;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", MYSQL_CONTAINER::getDriverClassName);
		registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
	}

}
