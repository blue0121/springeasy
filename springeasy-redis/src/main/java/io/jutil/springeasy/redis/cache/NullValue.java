package io.jutil.springeasy.redis.cache;

import org.springframework.cache.Cache;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Jin Zheng
 * @since 2024-05-23
 */
class NullValue implements Cache.ValueWrapper, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public static final NullValue INSTANCE = new NullValue();

	@Override
	public Object get() {
		return null;
	}
}
