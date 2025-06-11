package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.redis.pubsub.RedisPublisher;
import io.jutil.springeasy.redis.pubsub.RedisSubscribeListener;
import io.jutil.springeasy.redis.pubsub.RedisSubscriber;
import io.jutil.springeasy.redis.serializer.FastJsonRedisSerializer;
import io.jutil.springeasy.spring.config.SpringBeans;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(prefix = "springeasy.redis", name = "enabled", havingValue = "true")
public class RedisAutoConfiguration {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		var jsonSerializer = new FastJsonRedisSerializer<Object>();

		var redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setHashValueSerializer(jsonSerializer);
		redisTemplate.setValueSerializer(jsonSerializer);
		return redisTemplate;
	}

	@Bean
	public RedisPublisher redisPublisher(RedisProperties prop,
	                                     RedisTemplate<String, Object> redisTemplate) {
		return new RedisPublisher(prop, redisTemplate);
	}

	@Bean
	public RedisSubscriber redisSubscriber(RedisProperties prop,
	                                       RedisConnectionFactory factory,
	                                       List<RedisSubscribeListener<?>> listeners) {
		prop.check();
		var redisSubscriber = new RedisSubscriber(factory);
		var subscribers = prop.getSubscribers();
		if (subscribers == null || subscribers.isEmpty()) {
			return redisSubscriber;
		}

		for (var subscriber : subscribers) {
			var listener = SpringBeans.getBean(listeners, subscriber.getId());
			redisSubscriber.addListener(subscriber.getTopicType(), subscriber.getTopic(), listener);
		}
		return redisSubscriber;
	}

	@Bean(destroyMethod="shutdown")
	public RedissonClient redissonClient(RedisProperties prop) {
		prop.check();
		var config = new RedissonConfig(prop);
		var client = Redisson.create(config.getConfig());
		log.info("Redis connected, mode: {}", prop.getMode());
		return client;
	}

	/*@Bean
	public MutexFactory redisMutexFactory(StringRedisTemplate redisTemplate,
	                                      MutexProperties prop) {
		var factory = new RedisMutexFactory(redisTemplate, prop.);
		log.info("Create RedisMutexFactory");
		return factory;
	}*/

}
