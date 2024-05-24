package io.jutil.springeasy.redis.config;

import com.alibaba.fastjson2.JSONFactory;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.redission.JSONCodec;
import io.jutil.springeasy.core.util.JsonUtil;
import org.redisson.config.BaseConfig;
import org.redisson.config.Config;

/**
 * @author Jin Zheng
 * @since 2024-05-16
 */
public class RedissonConfig {
	private static final int MIN_SUBSCRIPTION_CONNECTION_POOL_SIZE = 2;
	private final RedisProperties prop;

	public RedissonConfig(RedisProperties prop) {
		this.prop = prop;
	}

	public Config getConfig() {
		var config = new Config();
		this.setCodec(config);

		switch (prop.getMode()) {
			case SINGLE -> this.setSingleConfig(config);
			case SENTINEL -> this.setSentinelConfig(config);
			case CLUSTER -> this.setClusterConfig(config);
		}
		return config;
	}

	private void setCodec(Config config) {
		var writeContext = JSONFactory.createWriteContext(
				JSONWriter.Feature.WriteClassName
		);
		var readContext = JSONFactory.createReadContext(JsonUtil.getAutoedTypeFilter());
		var codec = new JSONCodec(writeContext, readContext);
		config.setCodec(codec);
	}

	private void setSingleConfig(Config config) {
		var singleConfig = config.useSingleServer()
				.setAddress(prop.getServer());
		if (prop.getDatabase() > 0) {
			singleConfig.setDatabase(prop.getDatabase());
		}
		if (prop.getConnectionPoolSize() > 0) {
			singleConfig.setConnectionPoolSize(prop.getConnectionPoolSize());
		}
		if (prop.getSubscriptionConnectionPoolSize() > 0) {
			singleConfig.setSubscriptionConnectionPoolSize(prop.getSubscriptionConnectionPoolSize());
		}
		singleConfig.setSubscriptionConnectionMinimumIdleSize(MIN_SUBSCRIPTION_CONNECTION_POOL_SIZE);
		this.setBaseConfig(singleConfig);
	}

	private void setSentinelConfig(Config config) {
		var sentinelConfig = config.useSentinelServers()
				.setMasterName(prop.getMasterName())
				.addSentinelAddress(prop.getServers().toArray(new String[0]));
		if (prop.getDatabase() > 0) {
			sentinelConfig.setDatabase(prop.getDatabase());
		}
		if (prop.getConnectionPoolSize() > 0) {
			sentinelConfig.setMasterConnectionPoolSize(prop.getConnectionPoolSize())
					.setSlaveConnectionPoolSize(prop.getConnectionPoolSize());
		}
		if (prop.getSubscriptionConnectionPoolSize() > 0) {
			sentinelConfig.setSubscriptionConnectionPoolSize(prop.getSubscriptionConnectionPoolSize());
		}
		sentinelConfig.setSubscriptionConnectionMinimumIdleSize(MIN_SUBSCRIPTION_CONNECTION_POOL_SIZE);
		this.setBaseConfig(sentinelConfig);
	}

	private void setClusterConfig(Config config) {
		var clusterConfig = config.useClusterServers()
				.addNodeAddress(prop.getServers().toArray(new String[0]));
		if (prop.getConnectionPoolSize() > 0) {
			clusterConfig.setMasterConnectionPoolSize(prop.getConnectionPoolSize())
					.setSlaveConnectionPoolSize(prop.getConnectionPoolSize());
		}
		if (prop.getSubscriptionConnectionPoolSize() > 0) {
			clusterConfig.setSubscriptionConnectionPoolSize(prop.getSubscriptionConnectionPoolSize());
		}
		clusterConfig.setSubscriptionConnectionMinimumIdleSize(MIN_SUBSCRIPTION_CONNECTION_POOL_SIZE);
		this.setBaseConfig(clusterConfig);
	}

	private void setBaseConfig(BaseConfig<?> baseConfig) {
		if (prop.getPassword() != null && !prop.getPassword().isEmpty()) {
			baseConfig.setPassword(prop.getPassword());
		}
		if (prop.getTimeoutMillis() > 0) {
			baseConfig.setTimeout(prop.getTimeoutMillis())
					.setConnectTimeout(prop.getTimeoutMillis())
					.setIdleConnectionTimeout(prop.getTimeoutMillis());
		}
		if (prop.getRetry() > 0) {
			baseConfig.setRetryAttempts(prop.getRetry());
		}
	}
}
