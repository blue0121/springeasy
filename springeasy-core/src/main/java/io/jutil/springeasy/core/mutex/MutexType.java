package io.jutil.springeasy.core.mutex;

/**
 * @author Jin Zheng
 * @since 2025-05-28
 */
public enum MutexType {
	MEMORY,
	DATABASE,
	REDIS,
	;

	public static MutexType getType(String strType) {
		for (var type : MutexType.values()) {
			if (type.name().equalsIgnoreCase(strType)) {
				return type;
			}
		}
		return null;
	}
}
