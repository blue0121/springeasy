package io.jutil.springeasy.redis.pubsub;

import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.redis.RedisTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
@ActiveProfiles("pubsub")
@SpringBootTest(classes = {RedisTest.Application.class, RedisPubSubIT.Config.class})
public class RedisPubSubIT extends RedisTest {
	@Autowired
	User1SubscribeListener user1Listener;
	@Autowired
	User2SubscribeListener user2Listener;
	@Autowired
	StringSubscribeListener stringListener;

	@Autowired
	RedisPublisher publisher;

	final String channel = "user-topic";
	final String channel2 = "user-topic2";
	final String channel3 = "string-topic";

	@Configuration
	public static class Config {
		@Bean
		public User1SubscribeListener user1SubscribeListener() {
			return new User1SubscribeListener();
		}

		@Bean
		public User2SubscribeListener user2SubscribeListener() {
			return new User2SubscribeListener();
		}

		@Bean
		public StringSubscribeListener stringSubscribeListener() {
			return new StringSubscribeListener();
		}
	}

	@Test
	void testGetTopic() {
		Assertions.assertEquals(channel, publisher.getPublisherTopic("user"));
	}

	@Test
	void testUserPubSub() {
		var user = new User(1, "name");
		publisher.publish(channel, user);
		WaitUtil.sleep(500);

		Assertions.assertEquals(user, user1Listener.getUserMap().get(channel));
		Assertions.assertEquals(user, user2Listener.getUserMap().get(channel));
	}

	@Test
	void testUserPubSub2() {
		var user = new User(2, "name2");
		publisher.publish(channel2, user);
		WaitUtil.sleep(500);

		Assertions.assertEquals(user, user2Listener.getUserMap().get(channel2));
	}

	@Test
	void testStringPubSub() {
		var text = "Hello World!";
		publisher.publish(channel3, text);
		WaitUtil.sleep(500);

		Assertions.assertEquals(text, stringListener.getStringMap().get(channel3));
	}
}
