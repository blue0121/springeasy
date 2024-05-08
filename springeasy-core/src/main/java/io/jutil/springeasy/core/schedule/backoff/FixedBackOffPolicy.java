package io.jutil.springeasy.core.schedule.backoff;

import io.jutil.springeasy.core.util.DateUtil;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2024-04-28
 */
public class FixedBackOffPolicy implements BackOffPolicy {

	@Override
	public Type getType() {
		return Type.FIXED;
	}

	@Override
	public LocalDateTime nextExecutionTime(int retries) {
		var now = DateUtil.now();
		return now.plusMinutes(BASE * retries);
	}

}
