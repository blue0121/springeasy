package io.jutil.springeasy.redis.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@Getter
@NoArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "test1")
public class TestService {
	@Autowired
	TestRepository repository;

	@Cacheable(key = "'call_' + #key")
	public String call(String key) {
		var message = "call, key: " + key;
		log.info(message);
		repository.call(key);
		return message;
	}

	@Cacheable(key = "'empty_' + #key")
	public String empty(String key) {
		log.info("empty, key: {}", key);
		repository.call(key);
		return null;
	}

	@CacheEvict(allEntries = true)
	public void reset() {
		log.info("reset");
	}
}
