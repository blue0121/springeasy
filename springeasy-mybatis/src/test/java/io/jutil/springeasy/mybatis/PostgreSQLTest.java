package io.jutil.springeasy.mybatis;

import io.jutil.springeasy.mybatis.test.PostgreSQLTestContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@ActiveProfiles("postgresql")
@Testcontainers
public abstract class PostgreSQLTest extends BaseTest {

	@Container
	protected static final PostgreSQLTestContainer POSTGRESQL_CONTAINER = PostgreSQLTestContainer.POSTGRESQL_CONTAINER;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", POSTGRESQL_CONTAINER::getDriverClassName);
		registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
	}

}
