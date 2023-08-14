package io.jutil.springeasy.spring.config.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@Configuration
@EnableConfigurationProperties(ExecutorProperties.class)
@ConditionalOnProperty(prefix = "springeasy.executor", name = "enabled", havingValue = "true")
@SuppressWarnings("java:S1118")
public class ExecutorAutoConfiguration {

	@Bean
	public static ExecutorServiceRegistry executorServiceRegistry() {
		return new ExecutorServiceRegistry();
	}

}
