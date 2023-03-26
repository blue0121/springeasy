package io.jutil.springeasy.core.cache;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public interface RemovalListener<K, V> {

	void onRemoval(K key, V value, RemovalCause cause);
}
