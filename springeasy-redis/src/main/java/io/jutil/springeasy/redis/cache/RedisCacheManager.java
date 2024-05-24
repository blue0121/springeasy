package io.jutil.springeasy.redis.cache;

import io.jutil.springeasy.spring.config.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@Slf4j
public class RedisCacheManager implements CacheManager {
	private final CacheProperties prop;
	private final RedissonClient client;
	private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

	public RedisCacheManager(CacheProperties prop, RedissonClient client) {
		this.prop = prop;
		this.client = client;
	}

	@Override
	public Cache getCache(String name) {
		return cacheMap.computeIfAbsent(name, k -> {
			var mapCache = client.getMapCache(name);
			var config = prop.getItem(name);
			log.info("Create RedisCache, name: {}", name);
			return new RedisCache(mapCache, config);
		});
	}

	@Override
	public Collection<String> getCacheNames() {
		return cacheMap.keySet();
	}
}
