package io.jutil.springeasy.core.mq;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
public interface Consumer {

	void subscribe(String topic, ConsumerListener listener);

	void unsubscribe(String topic);

}
