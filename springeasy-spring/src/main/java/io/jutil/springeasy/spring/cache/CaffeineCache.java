package io.jutil.springeasy.spring.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
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
		if (cache instanceof LoadingCache<Object, Object> loadingCache) {
			return loadingCache.get(key);
		}
		return cache.getIfPresent(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		var call = new LoadFunction(valueLoader);
		var result = cache.get(key, call);
		return (T) this.fromStoreValue(result);
	}

	@Override
	public void put(Object key, Object value) {
		cache.put(key, this.toStoreValue(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		var call = new PutIfAbsentFunction(value);
		var result = cache.get(key, call);
		return call.called ? null : this.toValueWrapper(result);
	}

	@Override
	public void evict(Object key) {
		cache.invalidate(key);
	}

	@Override
	public boolean evictIfPresent(Object key) {
		var map = cache.asMap();
		return map.remove(key) != null;
	}

	@Override
	public void clear() {
		cache.invalidateAll();
	}

	@Override
	public boolean invalidate() {
		var empty = cache.asMap().isEmpty();
		cache.invalidateAll();
		return !empty;
	}

	private class PutIfAbsentFunction implements Function<Object, Object> {
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

	private class LoadFunction implements Function<Object, Object> {
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
