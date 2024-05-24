package io.jutil.springeasy.core.mq;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
public interface ConsumerListener {

	void onReceived(String topic, String message);
}
