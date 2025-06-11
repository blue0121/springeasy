package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.spring.config.BeanName;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
public interface RedisSubscribeListener<T> extends BeanName {

	void onReceived(RedisSubscribeTopic topic, T message);

	Class<T> getMessageType();

}
