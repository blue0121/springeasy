package io.jutil.springeasy.core.cache;

import java.util.Map;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public interface CacheLoader<K, V> {

	default V load(K key) throws Exception {
		var set = Set.of(key);
		var map = this.loadAll(set);
		return map.get(key);
	}

	@SuppressWarnings({"java:S1452", "java:S112"})
	Map<? extends K, ? extends V> loadAll(Set<? extends K> keys) throws Exception;
}
