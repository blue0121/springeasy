package io.jutil.springeasy.core.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public interface CacheBuilder {

	CacheBuilder expireAfterWrite(long val, TimeUnit unit);

	CacheBuilder expireAfterAccess(long val, TimeUnit unit);

	<K, V> CacheBuilder expire(Expiry<K, V> expiry);

	CacheBuilder maximumSize(long size);

	<K, V> CacheBuilder removalListener(RemovalListener<K, V> listener);

	<K, V> Cache<K, V> build();

	<K, V> Cache<K, V> build(CacheLoader<K, V> loader);
}
