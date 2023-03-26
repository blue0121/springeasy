package io.jutil.springeasy.internal.core.cache;

import io.jutil.springeasy.core.cache.RemovalCause;
import io.jutil.springeasy.core.cache.RemovalListener;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public class CaffeineRemovalListener <K, V>
		implements com.github.benmanes.caffeine.cache.RemovalListener<K, V> {
	private final RemovalListener<K, V> listener;

	public CaffeineRemovalListener(RemovalListener<K, V> listener) {
		this.listener = listener;
	}


	@Override
	public void onRemoval(K key, V value,
	                      com.github.benmanes.caffeine.cache.RemovalCause cause) {
		listener.onRemoval(key, value, RemovalCause.from(cause));
	}
}
