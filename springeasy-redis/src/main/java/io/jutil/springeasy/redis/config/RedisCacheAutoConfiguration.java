package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.redis.cache.RedisCacheManager;
import io.jutil.springeasy.spring.config.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnProperty(prefix = "springeasy.cache", name = "enabled", havingValue = "true")
@EnableCaching
public class RedisCacheAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "springeasy.cache", name = "type", havingValue = "redis")
	public CacheManager cacheManager(CacheProperties prop, RedissonClient client) {
		prop.check();
		var cacheManager = new RedisCacheManager(prop, client);
		log.info("CacheManager is: {}", cacheManager.getClass().getSimpleName());
		return cacheManager;
	}
}
