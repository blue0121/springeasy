package io.jutil.springeasy.mybatis.config;

import io.jutil.springeasy.mybatis.mutex.DatabaseMutexFactoryRegistry;
import io.jutil.springeasy.spring.config.mutex.MutexProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-06-10
 */
@Configuration
@EnableConfigurationProperties(MutexProperties.class)
@ConditionalOnProperty(prefix = "springeasy.mutex", name = "enabled", havingValue = "true")
@SuppressWarnings("java:S1118")
public class DatabaseMutexAutoConfiguration {

	@Bean
	public static DatabaseMutexFactoryRegistry databaseMutexFactoryRegistry() {
		return new DatabaseMutexFactoryRegistry();
	}

}
