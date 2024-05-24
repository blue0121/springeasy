package io.jutil.springeasy.core.mq;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
public interface Producer {

	void publish(String topic, String message);

	void publish(String topic, List<String> messageList);

	default void publishAsync(String topic, String message) {
		this.publishAsync(topic, message, null);
	}

	void publishAsync(String topic, String message, ProducerListener listener);

	default void publishAsync(String topic, List<String> messageList) {
		this.publishAsync(topic, messageList, null);
	}

	void publishAsync(String topic, List<String> messageList, ProducerListener listener);
}
