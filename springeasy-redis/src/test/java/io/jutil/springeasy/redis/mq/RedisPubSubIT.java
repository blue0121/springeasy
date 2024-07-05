package io.jutil.springeasy.redis.mq;

import io.jutil.springeasy.core.mq.ConsumerListener;
import io.jutil.springeasy.core.mq.ProducerListener;
import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.redis.Application;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@SpringBootTest(classes = Application.class)
class RedisPubSubIT {
	@Autowired
	RedisPubSub pubSub;

	String topic = "topic";
	String message = "message";

	@Test
	void testSync() {
		var listener = new Listener();
		pubSub.subscribe(topic, listener);
		pubSub.publish(topic, message);
		WaitUtil.sleep(500);
		Assertions.assertEquals(topic, listener.getTopic());
		Assertions.assertEquals(List.of(message), listener.getMessageList());
		pubSub.unsubscribe(topic);
	}

	@Test
	void testSyncList() {
		List<String> messageList = List.of(message, message);
		var listener = new Listener();
		pubSub.subscribe(topic, listener);
		pubSub.publish(topic, messageList);
		WaitUtil.sleep(500);
		Assertions.assertEquals(topic, listener.getTopic());
		Assertions.assertEquals(messageList, listener.getMessageList());
		pubSub.unsubscribe(topic);
	}

	@Test
	void testAsync() {
		var listener = new Listener();
		var listener2 = new Listener2();
		pubSub.subscribe(topic, listener);
		pubSub.publishAsync(topic, message, listener2);
		WaitUtil.sleep(500);
		Assertions.assertEquals(topic, listener.getTopic());
		Assertions.assertEquals(List.of(message), listener.getMessageList());

		Assertions.assertEquals(topic, listener2.getTopic());
		Assertions.assertEquals(List.of(message), listener2.getSuccessList());
		Assertions.assertTrue(listener2.getFailureList().isEmpty());
		pubSub.unsubscribe(topic);
	}

	@Test
	void testAsyncList() {
		List<String> messageList = List.of(message, message);
		var listener = new Listener();
		var listener2 = new Listener2();
		pubSub.subscribe(topic, listener);
		pubSub.publishAsync(topic, messageList, listener2);
		WaitUtil.sleep(500);
		Assertions.assertEquals(topic, listener.getTopic());
		Assertions.assertEquals(messageList, listener.getMessageList());

		Assertions.assertEquals(topic, listener2.getTopic());
		Assertions.assertEquals(messageList, listener2.getSuccessList());
		Assertions.assertTrue(listener2.getFailureList().isEmpty());
		pubSub.unsubscribe(topic);
	}

	@Getter
	static class Listener implements ConsumerListener {
		private String topic;
		private List<String> messageList = new ArrayList<>();

		@Override
		public void onReceived(String topic, String message) {
			this.topic = topic;
			this.messageList.add(message);
		}
	}

	@Getter
	static class Listener2 implements ProducerListener {
		private String topic;
		private List<String> successList = new ArrayList<>();
		private List<String> failureList = new ArrayList<>();

		@Override
		public void onSuccess(String topic, String message) {
			this.topic = topic;
			this.successList.add(message);
		}

		@Override
		public void onFailure(String topic, String message, Exception cause) {
			this.topic = topic;
			this.failureList.add(message);
		}
	}
}
