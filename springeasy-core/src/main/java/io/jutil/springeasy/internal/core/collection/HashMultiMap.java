package io.jutil.springeasy.internal.core.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@Slf4j
public class HashMultiMap<K, V> extends AbstractMultiMap<K, V> {
	private Class<?> mapType;

	public HashMultiMap(Map<K, Set<V>> map) {
		this.map = map;
		this.mapType = map.getClass();
		if (log.isDebugEnabled()) {
			log.debug("Use {}", map.getClass().getSimpleName());
		}
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public V put(K key, V value) {
		Set<V> set = null;
		if (map instanceof LinkedHashMap) {
			set = map.computeIfAbsent(key, k -> new LinkedHashSet<>());
		} else if (map instanceof HashMap) {
			set = map.computeIfAbsent(key, k -> new HashSet<>());
		} else {
			set = map.computeIfAbsent(key, k -> new ConcurrentHashSet<>());
		}
		set.add(value);
		return value;
	}

	@Override
	public boolean remove(K key) {
		Set<V> set = map.remove(key);
		return set != null;
	}

	@Override
	public boolean remove(K key, V value) {
		var rs = new AtomicBoolean();
		map.computeIfPresent(key, (k, set) -> {
			rs.set(set.remove(value));
			return set.isEmpty() ? null : set;
		});
		return rs.get();
	}

	@Override
	public Class<?> getMapType() {
		return mapType;
	}

}
