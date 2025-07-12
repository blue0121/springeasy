package io.jutil.springeasy.core.collection;

import io.jutil.springeasy.core.util.AssertUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
@SuppressWarnings("javaarchitecture:S7027")
class ImmutableMultiMap<K, V> extends AbstractMultiMap<K, V> {
	ImmutableMultiMap(MultiMap<K, V> map) {
		AssertUtil.notNull(map, "MultiMap");
		this.map = switch (map.getMapType().getSimpleName()) {
			case "LinkedHashMap" -> new LinkedHashMap<>();
			default -> new HashMap<>();
		};

		for (var entry : map.entrySet()) {
			this.map.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException("put");
	}

	@Override
	public boolean remove(K key) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean remove(K key, V value) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public Class<?> getMapType() {
		return map.getClass();
	}

}
