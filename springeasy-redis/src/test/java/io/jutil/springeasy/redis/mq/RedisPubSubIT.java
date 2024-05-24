package io.jutil.springeasy.redis.mq;

import io.jutil.springeasy.core.mq.ConsumerListener;
import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.redis.Application;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	void test1() {
		var listener = new Listener();
		pubSub.subscribe(topic, listener);
		pubSub.publish(topic, message);
		WaitUtil.sleep(500);
		Assertions.assertEquals(topic, listener.getTopic());
		Assertions.assertEquals(message, listener.getMessage());
		pubSub.unsubscribe(topic);
	}

	@Getter
	class Listener implements ConsumerListener {
		private String topic;
		private String message;

		@Override
		public void onReceived(String topic, String message) {
			this.topic = topic;
			this.message = message;
		}
	}
}
