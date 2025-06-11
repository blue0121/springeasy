package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.redis.pubsub.TopicType;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import io.jutil.springeasy.spring.config.PropertiesUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.redis")
public class RedisProperties implements PropertiesChecker {
	private Map<String, String> publisherTopics;
	private List<Subscriber> subscribers;



	private String server;
	private List<String> servers;
	private RedisMode mode = RedisMode.SINGLE;
	private String masterName;
	private int database = 0;
	private String password;
	private int timeoutMillis;
	private int subscriptionConnectionPoolSize;
	private int connectionPoolSize;
	private int retry;

	@Override
	public void check() {
		PropertiesUtil.check(subscribers);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Subscriber implements PropertiesChecker {
		private boolean enabled = true;
		private String id;
		private String topic;
		private String type = TopicType.CHANNEL.name();
		private TopicType topicType;

		@Override
		public void check() {
			if (!enabled) {
				return;
			}
			AssertUtil.notEmpty(id, "id");
			AssertUtil.notEmpty(topic, "topic");
			AssertUtil.notEmpty(type, "type");
			this.topicType = TopicType.getType(type);
			AssertUtil.notNull(topicType, "TopicType");
		}
	}
}
