package io.jutil.springeasy.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jin Zheng
 * @since 2025-10-17
 */
public class ListUtil {
	private ListUtil() {}

	public static <K, V> Map<K, V> toMap(List<V> entityList, Function<V, K> f) {
		Map<K, V> map = new HashMap<>();
		if (entityList == null || entityList.isEmpty()) {
			return map;
		}
		for (V entity : entityList) {
			if (entity == null) {
				continue;
			}
			K key = f.apply(entity);
			map.put(key, entity);
		}
		return map;
	}

	public static <K, V> Map<K, List<V>> groupMap(List<V> entityList, Function<V, K> f) {
		Map<K, List<V>> map = new HashMap<>();
		if (entityList == null || entityList.isEmpty()) {
			return map;
		}
		for (V entity : entityList) {
			if (entity == null) {
				continue;
			}
			var key = f.apply(entity);
			var list = map.computeIfAbsent(key, k -> new ArrayList<>());
			list.add(entity);
		}
		return map;
	}
}
