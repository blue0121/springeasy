package io.jutil.springeasy.core.schedule.backoff;

import io.jutil.springeasy.core.util.DateUtil;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2024-04-28
 */
public class ExponentialBackOffPolicy implements BackOffPolicy {

	@Override
	public Type getType() {
		return Type.EXPONENTIAL;
	}

	@Override
	public LocalDateTime nextExecutionTime(int retries) {
		var now = DateUtil.now();
		var interval = (long) Math.pow(BASE, retries);
		return now.plusMinutes(interval);
	}

}
