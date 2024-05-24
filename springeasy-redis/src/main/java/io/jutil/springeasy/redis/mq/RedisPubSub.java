package io.jutil.springeasy.redis.mq;

import io.jutil.springeasy.core.mq.Consumer;
import io.jutil.springeasy.core.mq.ConsumerListener;
import io.jutil.springeasy.core.mq.Producer;
import io.jutil.springeasy.core.mq.ProducerListener;
import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@Slf4j
public class RedisPubSub implements Producer, Consumer {
	private final RedissonClient client;
	private final Map<String, Integer> topicMap = new ConcurrentHashMap<>();

	public RedisPubSub(RedissonClient client) {
		this.client = client;
	}

	@Override
	public void publish(String topic, String message) {
		var rTopic = this.getTopic(topic);
		rTopic.publish(message);
	}

	@Override
	public void publish(String topic, List<String> messageList) {
		if (messageList.isEmpty()) {
			return;
		}

		var rTopic = this.getTopic(topic);
		var latch = new CountDownLatch(messageList.size());
		for (var message : messageList) {
			var future = rTopic.publishAsync(message);
			future.thenAccept(r -> latch.countDown());
		}
		WaitUtil.await(latch);
	}

	@Override
	public void publishAsync(String topic, String message, ProducerListener listener) {
		var rTopic = this.getTopic(topic);
		var future = rTopic.publishAsync(message);
		if (listener != null) {
			future.thenAccept(r -> listener.onSuccess(topic, message));
		}
	}

	@Override
	public void publishAsync(String topic, List<String> messageList, ProducerListener listener) {
		if (messageList.isEmpty()) {
			return;
		}

		var rTopic = this.getTopic(topic);
		for (var message : messageList) {
			var future = rTopic.publishAsync(message);
			if (listener != null) {
				future.thenAccept(r -> listener.onSuccess(topic, message));
			}
		}
	}

	@Override
	public void subscribe(String topic, ConsumerListener listener) {
		var rTopic = this.getTopic(topic);
		var rs = rTopic.addListener(String.class, (t, m) -> {
			if (log.isDebugEnabled()) {
				log.debug("Redis received, topic: {}, message: {}", t, m);
			} else {
				log.info("Redis received, topic: {}", t);
			}

			listener.onReceived(t.toString(), m);
		});
		topicMap.put(topic, rs);
	}

	@Override
	public void unsubscribe(String topic) {
		var id = topicMap.get(topic);
		if (id == null) {
			return;
		}

		var rTopic = this.getTopic(topic);
		rTopic.removeListener(id);
	}

	private RTopic getTopic(String topic) {
		return client.getTopic(topic);
	}
}
