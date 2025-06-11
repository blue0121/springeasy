package io.jutil.springeasy.spring.config.mutex;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-05-30
 */
@Configuration
@EnableConfigurationProperties(MutexProperties.class)
@ConditionalOnProperty(prefix = "springeasy.mutex", name = "enabled", havingValue = "true")
@SuppressWarnings("java:S1118")
public class MutexAutoConfiguration {

	@Bean
	public static MemoryMutexFactoryRegistry mutexFactoryRegistry() {
		return new MemoryMutexFactoryRegistry();
	}

}
