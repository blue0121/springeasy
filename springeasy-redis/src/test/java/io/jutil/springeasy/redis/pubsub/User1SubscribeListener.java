package io.jutil.springeasy.redis.pubsub;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
@Getter
public class User1SubscribeListener implements RedisSubscribeListener<User> {
	private final Map<String, User> userMap = new HashMap<>();

	@Override
	public void onReceived(RedisSubscribeTopic topic, User message) {
		userMap.put(topic.channel(), message);
	}

	@Override
	public String getId() {
		return "user1";
	}

	@Override
	public Class<User> getMessageType() {
		return User.class;
	}
}
