package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class MessageListenerAgent implements MessageListener {
	private final RedisSubscribeListener listener;

	public MessageListenerAgent(RedisSubscribeListener listener) {
		this.listener = listener;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onMessage(Message message, byte[] pattern) {
		var strChannel = new String(message.getChannel());
		var strPattern = pattern != null ? new String(pattern) : null;

		if (log.isDebugEnabled()) {
			var strBody = new String(message.getBody());
			log.debug("Redis subscriber received, pattern: {}, channel: {}, body: {}",
					strPattern, strChannel, strBody);
		} else {
			log.debug("Redis subscriber received, pattern: {}, channel: {}", strPattern, strChannel);
		}

		var topic = new RedisSubscribeTopic(strPattern, strChannel);
		try {
			var body = JsonUtil.fromBytes(message.getBody(), listener.getMessageType());
			listener.onReceived(topic, body);
		} catch (Exception e) {
			log.error("Redis subscribe error, pattern: {}, channel: {}", strPattern, strChannel, e);
		}
	}
}
