package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.core.util.JsonUtil;
import io.jutil.springeasy.redis.config.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jin Zheng
 * @since 2025-05-06
 */
@Slf4j
public class RedisPublisher {
	private final RedisProperties prop;
	private final RedisTemplate<String, Object> redisTemplate;

	public RedisPublisher(RedisProperties prop, RedisTemplate<String, Object> redisTemplate) {
		this.prop = prop;
		this.redisTemplate = redisTemplate;
	}

	public String getPublisherTopic(String key) {
		var topics = prop.getPublisherTopics();
		if (topics == null || topics.isEmpty()) {
			return null;
		}
		return topics.get(key);
	}

	public void publish(String channel, Object payload) {
		if (log.isDebugEnabled()) {
			log.debug("Redis publishing, channel: {}, payload: {}", channel, JsonUtil.toString(payload));
		} else {
			log.info("Redis publishing, channel: {}", channel);
		}

		var clients = redisTemplate.convertAndSend(channel, payload);
		log.info("Redis published successfully, channel: {}, clients: {}", channel, clients);
	}
}
