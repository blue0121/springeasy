package io.jutil.springeasy.spring.config.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2022-12-22
 */
@SuppressWarnings("java:S1118")
@Configuration
@EnableConfigurationProperties(HttpProperties.class)
@ConditionalOnProperty(prefix = "springeasy.http", name = "enabled", havingValue = "true")
public class HttpTemplateAutoConfiguration {

	@Bean
	public static HttpTemplateRegistry httpTemplateRegistry() {
		return new HttpTemplateRegistry();
	}
}
