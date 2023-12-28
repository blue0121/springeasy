package io.jutil.springeasy.core.cache;


/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
class CaffeineExpiry<K, V>
		implements com.github.benmanes.caffeine.cache.Expiry<K, V> {
	private static final long NANO = 1_000_000;

	private final Expiry<K, V> expiry;

	CaffeineExpiry(Expiry<K, V> expiry) {
		this.expiry = expiry;
	}

	@Override
	public long expireAfterCreate(K key, V value, long currentTime) {
		long currentTimeMillis = currentTime / NANO;
		return expiry.expireAfterCreate(key, value, currentTimeMillis) * NANO;
	}

	@Override
	public long expireAfterUpdate(K key, V value, long currentTime, long currentDuration) {
		long currentTimeMillis = currentTime / NANO;
		long currentDurationMillis = currentDuration / NANO;
		return expiry.expireAfterUpdate(key, value, currentTimeMillis, currentDurationMillis) * NANO;
	}

	@Override
	public long expireAfterRead(K key, V value, long currentTime, long currentDuration) {
		long currentTimeMillis = currentTime / NANO;
		long currentDurationMillis = currentDuration / NANO;
		return expiry.expireAfterRead(key, value, currentTimeMillis, currentDurationMillis) * NANO;
	}
}
