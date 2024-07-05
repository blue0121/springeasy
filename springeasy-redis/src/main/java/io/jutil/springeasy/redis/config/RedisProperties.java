package io.jutil.springeasy.redis.config;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.spring.config.PropertiesChecker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("springeasy.redis")
public class RedisProperties implements PropertiesChecker {
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
		switch (mode) {
			case SINGLE -> AssertUtil.notEmpty(server, "Server");
			case SENTINEL -> {
				AssertUtil.notEmpty(servers, "Servers");
				AssertUtil.notEmpty(masterName, "MasterName");
			}
			case CLUSTER -> AssertUtil.notEmpty(servers, "Servers");
		}

		AssertUtil.nonNegative(timeoutMillis, "Timeout");
		AssertUtil.nonNegative(subscriptionConnectionPoolSize, "SubscriptionConnectionPoolSize");
		AssertUtil.nonNegative(connectionPoolSize, "ConnectionPoolSize");
		AssertUtil.nonNegative(retry, "Retry");
	}
}
