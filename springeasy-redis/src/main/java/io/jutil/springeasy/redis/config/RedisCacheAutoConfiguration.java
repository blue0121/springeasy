package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.redis.serializer.FastJsonRedisSerializer;
import io.jutil.springeasy.spring.config.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
	public CacheManager cacheManager(CacheProperties prop, RedisConnectionFactory factory) {
		prop.check();
		var config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(prop.getExpireSec()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
						new FastJsonRedisSerializer<>())
				);

		Map<String, RedisCacheConfiguration> cache = new HashMap<>();
		if (prop.getItems() != null && !prop.getItems().isEmpty()) {
			for (var item : prop.getItems()) {
				cache.put(item.getId(), config.entryTtl(Duration.ofSeconds(item.getExpireSec())));
			}
		}
		var cacheManager = RedisCacheManager.builder(factory)
				.cacheDefaults(config)
				.withInitialCacheConfigurations(cache).build();
		log.info("CacheManager is: {}", cacheManager.getClass().getSimpleName());
		return cacheManager;
	}
}
