package io.jutil.springeasy.core.cache;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public interface Expiry<K, V> {

	long expireAfterCreate(K key, V value, long currentTimeMillis);

	long expireAfterUpdate(K key, V value, long currentTimeMillis, long currentDurationMillis);

	default long expireAfterRead(K key, V value, long currentTimeMillis, long currentDurationMillis) {
		return currentDurationMillis;
	}
}
