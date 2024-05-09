package io.jutil.springeasy.core.schedule.backoff;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2024-04-28
 */
public interface BackOffPolicy {
	long BASE = 2L;

	Type getType();

	LocalDateTime nextExecutionTime(int retries);

	enum Type {
		FIXED,
		EXPONENTIAL,
		;

		public static Type from(String str) {
			for (Type type : Type.values()) {
				if (str.equalsIgnoreCase(type.name())) {
					return type;
				}
			}
			return null;
		}
	}
}
