package io.jutil.springeasy.spring.config.cache;

import io.jutil.springeasy.spring.cache.CaffeineCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "springeasy.cache", name = "enabled", havingValue = "true")
@EnableCaching
public class CacheAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "springeasy.cache", name = "type", havingValue = "local")
	public CacheManager cacheManager(CacheProperties prop) {
		prop.check();
		var cacheManager = new CaffeineCacheManager(prop);
		log.info("CacheManager is: {}", cacheManager.getClass().getSimpleName());
		return cacheManager;
	}
}
