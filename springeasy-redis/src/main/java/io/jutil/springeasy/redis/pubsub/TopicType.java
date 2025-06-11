package io.jutil.springeasy.redis.pubsub;

/**
 * @author Jin Zheng
 * @since 2025-05-07
 */
public enum TopicType {
	CHANNEL,
	PATTERN,

	;

	public static TopicType getType(String str) {
		for (var type : TopicType.values()) {
			if (type.name().equalsIgnoreCase(str)) {
				return type;
			}
		}
		return null;
	}
}
