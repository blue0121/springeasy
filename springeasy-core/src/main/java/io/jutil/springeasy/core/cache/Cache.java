package io.jutil.springeasy.core.cache;

import io.jutil.springeasy.internal.core.cache.CaffeineCacheBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public interface Cache<K, V> {

	static CacheBuilder builder() {
		return new CaffeineCacheBuilder();
	}

	V get(K key);

	Map<K, V> getAll(Collection<? extends K> keys);

	void put(K key, V value);

	void putAll(Map<? extends K, ? extends V> map);

	void refresh(K key);

	void refreshAll(Collection<? extends K> keys);

	void remove(K key);

	void removeAll(Collection<? extends K> keys);

	void clear();

}
