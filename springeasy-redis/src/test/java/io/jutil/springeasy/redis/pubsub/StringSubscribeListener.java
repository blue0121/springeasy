package io.jutil.springeasy.redis.pubsub;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
@Getter
public class StringSubscribeListener extends RedisSubscribeListenerAdapter<String> {
	private final Map<String, String> stringMap = new HashMap<>();

	@Override
	public void onReceived(RedisSubscribeTopic topic, String message) {
		stringMap.put(topic.channel(), message);
	}

	@Override
	public String getId() {
		return "string";
	}

}
