package io.jutil.springeasy.core.mq;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
public interface ProducerListener {

	void onSuccess(String topic, String message);

	void onFailure(String topic, String message, Exception cause);

}
