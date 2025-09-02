package io.jutil.springeasy.test.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Jin Zheng
 * @since 2025-09-02
 */
@ActiveProfiles("postgresql")
@ContextConfiguration(initializers = PostgreSQLTest.Initializer.class)
public interface PostgreSQLTest {

	class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext ctx) {
			TestPropertyValues.of(PostgreSQLTestContainer.getProperties()).applyTo(ctx);
		}
	}

}
