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
@ActiveProfiles("mysql")
@Testcontainers
public abstract class BaseMySQLTest {
	@Container
	public static final MySQLTestContainer MYSQL = MySQLTestContainer.CONTAINER;

	@DynamicPropertySource
	public static void dynamicProperty(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);
		registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
		registry.add("spring.datasource.username", MYSQL::getUsername);
		registry.add("spring.datasource.password", MYSQL::getPassword);
		log.info(">>>>>>>>> MySQL Database, jdbc url: {}, driver: {}",
				MYSQL.getJdbcUrl(), MYSQL.getDriverClassName());
	}

}
