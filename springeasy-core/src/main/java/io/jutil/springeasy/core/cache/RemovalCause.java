package io.jutil.springeasy.core.cache;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public enum RemovalCause {
	EXPLICIT(false),
	REPLACED(false),
	COLLECTED(true),
	EXPIRED(true),
	SIZE(true),
	;

	private boolean evicted;

	RemovalCause(boolean evicted) {
		this.evicted = evicted;
	}

	public static RemovalCause from(com.github.benmanes.caffeine.cache.RemovalCause cause) {
		for (var c : RemovalCause.values()) {
			if (c.name().equals(cause.name())) {
				return c;
			}
		}
		return null;
	}

	public boolean isEvicted() {
		return evicted;
	}
}
