package io.jutil.springeasy.internal.core.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.jutil.springeasy.core.cache.Cache;
import io.jutil.springeasy.core.cache.CacheBuilder;
import io.jutil.springeasy.core.cache.CacheLoader;
import io.jutil.springeasy.core.cache.Expiry;
import io.jutil.springeasy.core.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
public class CaffeineCacheBuilder implements CacheBuilder {
	private Caffeine<Object, Object> caffeine = Caffeine.newBuilder();

	public CaffeineCacheBuilder() {
	}

	@Override
	public CaffeineCacheBuilder expireAfterWrite(long val, TimeUnit unit) {
		caffeine.expireAfterWrite(val, unit);
		return this;
	}

	@Override
	public CaffeineCacheBuilder expireAfterAccess(long val, TimeUnit unit) {
		caffeine.expireAfterAccess(val, unit);
		return this;
	}

	@Override
	public <K, V> CaffeineCacheBuilder expire(Expiry<K, V> expiry) {
		caffeine.expireAfter(new CaffeineExpiry<>(expiry));
		return this;
	}

	@Override
	public CaffeineCacheBuilder maximumSize(long size) {
		caffeine.maximumSize(size);
		return this;
	}

	@Override
	public <K, V> CaffeineCacheBuilder removalListener(RemovalListener<K, V> listener) {
		caffeine.removalListener(new CaffeineRemovalListener<>(listener));
		return this;
	}

	@Override
	public <K, V> Cache<K, V> build() {
		return new CaffeineCache<>(caffeine.build());
	}

	@Override
	public <K, V> Cache<K, V> build(CacheLoader<K, V> loader) {
		return new CaffeineCache<>(caffeine.build(new CaffeineCacheLoader<>(loader)));
	}
}
