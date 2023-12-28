package io.jutil.springeasy.core.collection;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public interface MultiMap<K, V> {

	static <K, V> MultiMap<K, V> create() {
		return new HashMultiMap<>(new HashMap<>());
	}

	static <K, V> MultiMap<K, V> createLinked() {
		return new HashMultiMap<>(new LinkedHashMap<>());
	}


	static <K, V> MultiMap<K, V> createConcurrent() {
		return new HashMultiMap<>(new ConcurrentHashMap<>());
	}

	void clear();

	boolean containsKey(K key);

	Set<Map.Entry<K, Set<V>>> entrySet();

	Set<V> get(K key);

	V getOne(K key);

	boolean isEmpty();

	V put(K key, V value);

	boolean remove(K key);

	boolean remove(K key, V value);

	int size();

	Class<?> getMapType();

	static <K, V> MultiMap<K, V> copyOf(MultiMap<K, V> map) {
		return new ImmutableMultiMap<>(map);
	}

}
