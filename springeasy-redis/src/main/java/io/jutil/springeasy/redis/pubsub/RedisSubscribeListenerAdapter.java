package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.spring.util.ReflectionUtil;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
public abstract class RedisSubscribeListenerAdapter<T> implements RedisSubscribeListener<T> {

	@Override
	@SuppressWarnings("unchecked")
	public final Class<T> getMessageType() {
		return (Class<T>) ReflectionUtil.getActualTypeArguments(this)[0];
	}

}
