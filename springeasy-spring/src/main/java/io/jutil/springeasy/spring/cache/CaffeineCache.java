package io.jutil.springeasy.spring.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;
import java.util.function.UnaryOperator;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@Slf4j
public final class CaffeineCache extends AbstractValueAdaptingCache {
	private final String name;
	private final Cache<Object, Object> cache;

	public CaffeineCache(String name, Cache<Object, Object> cache) {
		super(true);
		this.name = name;
		this.cache = cache;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getNativeCache() {
		return cache;
	}

	@Override
	protected Object lookup(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache lookup key: {}, name: {}", key, name);
		}
		if (cache instanceof LoadingCache<Object, Object> loadingCache) {
			return loadingCache.get(key);
		}
		return cache.getIfPresent(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		if (log.isDebugEnabled()) {
			log.debug("Cache get key: {}, name: {}", key, name);
		}
		var call = new LoadFunction(valueLoader);
		var result = cache.get(key, call);
		return (T) this.fromStoreValue(result);
	}

	@Override
	public void put(Object key, Object value) {
		if (log.isDebugEnabled()) {
			log.debug("Cache put key: {}, name: {}", key, name);
		}
		cache.put(key, this.toStoreValue(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		if (log.isDebugEnabled()) {
			log.debug("Cache putIfAbsent key: {}, name: {}", key, name);
		}
		var call = new PutIfAbsentFunction(value);
		var result = cache.get(key, call);
		return call.called ? null : this.toValueWrapper(result);
	}

	@Override
	public void evict(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache evict key: {}, name: {}", key, name);
		}
		cache.invalidate(key);
	}

	@Override
	public boolean evictIfPresent(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache evictIfPresent key: {}, name: {}", key, name);
		}
		var map = cache.asMap();
		return map.remove(key) != null;
	}

	@Override
	public void clear() {
		if (log.isDebugEnabled()) {
			log.debug("Cache clear");
		}
		cache.invalidateAll();
	}

	@Override
	public boolean invalidate() {
		if (log.isDebugEnabled()) {
			log.debug("Cache invalidate");
		}
		var empty = cache.asMap().isEmpty();
		cache.invalidateAll();
		return !empty;
	}

	private class PutIfAbsentFunction implements UnaryOperator<Object> {
		boolean called;
		private final Object value;

		public PutIfAbsentFunction(Object value) {
			this.value = value;
		}


		@Override
		public Object apply(Object key) {
			this.called = true;
			return toStoreValue(value);
		}
	}

	private class LoadFunction implements UnaryOperator<Object> {
		private final Callable<?> loader;

		public LoadFunction(Callable<?> loader) {
			this.loader = loader;
		}

		@Override
		public Object apply(Object key) {
			try {
				return loader.call();
			} catch (Exception e) {
				return new ValueRetrievalException(key, loader, e);
			}
		}
	}
}
