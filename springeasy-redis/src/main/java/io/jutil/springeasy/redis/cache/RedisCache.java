package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.spring.config.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2024-05-23
 */
@Slf4j
public class RedisCache implements Cache {
	private final RMapCache<Object, Object> mapCache;
	private final CacheProperties.CacheItemProperties config;

	public RedisCache(RMapCache<Object, Object> mapCache,
	                  CacheProperties.CacheItemProperties config) {
		this.mapCache = mapCache;
		this.config = config;
	}

	@Override
	public String getName() {
		return mapCache.getName();
	}

	@Override
	public Object getNativeCache() {
		return mapCache;
	}

	@Override
	public ValueWrapper get(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache get key: {}, name: {}", key, mapCache.getName());
		}
		var value = mapCache.getWithTTLOnly(key);
		return this.toValueWrapper(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		if (log.isDebugEnabled()) {
			log.debug("Cache get with type key: {}, name: {}", key, mapCache.getName());
		}
		var value = mapCache.getWithTTLOnly(key);
		if (value == null) {
			return null;
		}

		return (T) this.fromStoreValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		if (log.isDebugEnabled()) {
			log.debug("Cache get with valueLoader key: {}, name: {}", key, mapCache.getName());
		}
		var value = mapCache.getWithTTLOnly(key);
		if (value != null) {
			return (T) this.fromStoreValue(value);
		}
		var lock = mapCache.getLock(key);
		lock.lock();
		try {
			value = mapCache.getWithTTLOnly(key);
			if (value != null) {
				return (T) this.fromStoreValue(value);
			}
			try {
				value = valueLoader.call();
			} catch (Exception e) {
				throw new ValueRetrievalException(key, valueLoader, e);
			}
			var storeValue = this.toStoreValue(value);
			mapCache.fastPut(key, storeValue, config.getExpireSec(), TimeUnit.SECONDS);
			return (T) this.fromStoreValue(value);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(Object key, Object value) {
		if (log.isDebugEnabled()) {
			log.debug("Cache put key: {}, name: {}", key, mapCache.getName());
		}
		var storeValue = this.toStoreValue(value);
		mapCache.fastPut(key, storeValue, config.getExpireSec(), TimeUnit.SECONDS);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		if (log.isDebugEnabled()) {
			log.debug("Cache putIfAbsent key: {}, name: {}", key, mapCache.getName());
		}
		var storeValue = this.toStoreValue(value);
		var prevValue = mapCache.putIfAbsent(key, storeValue, config.getExpireSec(), TimeUnit.SECONDS);
		return this.toValueWrapper(prevValue);
	}

	@Override
	public void evict(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache evict key: {}, name: {}", key, mapCache.getName());
		}
		mapCache.fastRemove(key);
	}

	@Override
	public boolean evictIfPresent(Object key) {
		if (log.isDebugEnabled()) {
			log.debug("Cache evictIfPresent key: {}, name: {}", key, mapCache.getName());
		}
		var count = mapCache.fastRemove(key);
		return count > 0;
	}

	@Override
	public void clear() {
		if (log.isDebugEnabled()) {
			log.debug("Cache clear, name: {}", mapCache.getName());
		}
		mapCache.clear();
	}

	protected ValueWrapper toValueWrapper(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof NullValue nullValue) {
			return nullValue;
		}
		return new SimpleValueWrapper(value);
	}

	protected Object fromStoreValue(Object storeValue) {
		if (storeValue instanceof NullValue) {
			return null;
		}
		return storeValue;
	}

	protected Object toStoreValue(Object userValue) {
		if (userValue == null) {
			return NullValue.INSTANCE;
		}
		return userValue;
	}

}
