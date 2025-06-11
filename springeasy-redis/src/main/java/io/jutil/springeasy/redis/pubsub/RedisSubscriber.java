package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin Zheng
 * @since 2025-05-06
 */
@Slf4j
public class RedisSubscriber implements DisposableBean {
	private final RedisMessageListenerContainer container;
	private final Map<Topic, MessageListener> listenerMap = new ConcurrentHashMap<>();

	public RedisSubscriber(RedisConnectionFactory factory) {
		this.container = new RedisMessageListenerContainer();
		container.setConnectionFactory(factory);
		container.afterPropertiesSet();
		container.start();
	}

	public void addListener(TopicType type, String topic, RedisSubscribeListener<?> listener) {
		AssertUtil.notNull(type, "TopicType");
		AssertUtil.notEmpty(topic, "Topic");
		AssertUtil.notNull(listener, "RedisSubscribeListener");

		Topic redisTopic = switch (type) {
			case CHANNEL -> new ChannelTopic(topic);
			case PATTERN -> new PatternTopic(topic);
		};
		var listenerAgent = new MessageListenerAgent(listener);
		container.addMessageListener(listenerAgent, redisTopic);
		listenerMap.put(redisTopic, listenerAgent);
		log.info("Redis add subscriber topic: {}, type: {}, id: {}", topic, type, listener.getId());
	}

	public void removeListener(TopicType type, String topic) {
		AssertUtil.notNull(type, "TopicType");
		AssertUtil.notEmpty(topic, "Topic");

		Topic redisTopic = switch (type) {
			case CHANNEL -> new ChannelTopic(topic);
			case PATTERN -> new PatternTopic(topic);
		};
		var listener = listenerMap.remove(redisTopic);
		container.removeMessageListener(listener, redisTopic);
		log.info("Redis remove subscriber topic: {}, type: {}", topic, type);
	}

	@Override
	public void destroy() throws Exception {
		container.destroy();
	}
}
