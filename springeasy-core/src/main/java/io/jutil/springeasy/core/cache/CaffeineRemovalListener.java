package io.jutil.springeasy.core.cache;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
class CaffeineRemovalListener <K, V>
		implements com.github.benmanes.caffeine.cache.RemovalListener<K, V> {
	private final RemovalListener<K, V> listener;

	CaffeineRemovalListener(RemovalListener<K, V> listener) {
		this.listener = listener;
	}


	@Override
	public void onRemoval(K key, V value,
	                      com.github.benmanes.caffeine.cache.RemovalCause cause) {
		listener.onRemoval(key, value, RemovalCause.from(cause));
	}
}
