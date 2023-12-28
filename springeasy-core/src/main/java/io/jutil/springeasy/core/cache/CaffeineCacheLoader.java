package io.jutil.springeasy.core.cache;

import java.util.Map;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
class CaffeineCacheLoader<K, V>
		implements com.github.benmanes.caffeine.cache.CacheLoader<K, V> {
	private final CacheLoader<K, V> loader;

	CaffeineCacheLoader(CacheLoader<K, V> loader) {
		this.loader = loader;
	}

	@Override
	public V load(K key) throws Exception {
		return loader.load(key);
	}

	@Override
	public Map<? extends K, ? extends V> loadAll(Set<? extends K> keys) throws Exception {
		return loader.loadAll(keys);
	}
}
