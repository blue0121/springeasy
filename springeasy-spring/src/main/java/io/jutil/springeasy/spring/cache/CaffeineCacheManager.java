package io.jutil.springeasy.spring.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.jutil.springeasy.spring.config.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@Slf4j
public final class CaffeineCacheManager implements CacheManager {
	private final CacheProperties prop;
	private final Caffeine<Object, Object> builder = Caffeine.newBuilder();
	private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

	public CaffeineCacheManager(CacheProperties prop) {
		this.prop = prop;
	}

	@Override
	public Cache getCache(String name) {
		return cacheMap.computeIfAbsent(name, k -> {
			var config = prop.getItem(k);
			var cache = builder.maximumSize(config.getMaxSize())
					.expireAfterAccess(config.getExpireSec(), TimeUnit.SECONDS)
					.expireAfterWrite(config.getExpireSec(), TimeUnit.SECONDS)
					.build();
			log.info("Create CaffeineCache, name: {}", name);
			return new CaffeineCache(k, cache);
		});
	}

	@Override
	public Collection<String> getCacheNames() {
		return cacheMap.keySet();
	}
}
