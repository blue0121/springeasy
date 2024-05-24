package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.redis.mq.RedisPubSub;
import io.jutil.springeasy.redis.mutex.RedisMutexFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(prefix = "springeasy.redis", name = "enabled", havingValue = "true")
public class RedisAutoConfiguration {

	@Bean(destroyMethod="shutdown")
	public RedissonClient redissonClient(RedisProperties prop) {
		prop.check();
		var config = new RedissonConfig(prop);
		var client = Redisson.create(config.getConfig());
		log.info("Redis connected, mode: {}", prop.getMode());
		return client;
	}

	@Bean
	public MutexFactory redisMutexFactory(RedissonClient client) {
		var factory = new RedisMutexFactoryImpl(client);
		log.info("Create RedisMutexFactory");
		return factory;
	}

	@Bean
	public RedisPubSub redisPubSub(RedissonClient client) {
		return new RedisPubSub(client);
	}
}
