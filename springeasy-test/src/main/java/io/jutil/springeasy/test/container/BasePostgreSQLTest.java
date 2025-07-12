package io.jutil.springeasy.test.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@Slf4j
@ActiveProfiles("postgresql")
@Testcontainers
public abstract class BasePostgreSQLTest {
	@Container
	public static final PostgreSQLTestContainer POSTGRESQL = PostgreSQLTestContainer.CONTAINER;

	@DynamicPropertySource
	public static void dynamicProperty(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", POSTGRESQL::getDriverClassName);
		registry.add("spring.datasource.url", POSTGRESQL::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRESQL::getUsername);
		registry.add("spring.datasource.password", POSTGRESQL::getPassword);
		log.info(">>>>>>>>> PostgreSQL Database, jdbc url: {}, driver: {}",
				POSTGRESQL.getJdbcUrl(), POSTGRESQL.getDriverClassName());
	}

}
