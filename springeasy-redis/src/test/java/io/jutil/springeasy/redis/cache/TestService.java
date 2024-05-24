package io.jutil.springeasy.redis.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Jin Zheng
 * @since 2024-05-24
 */
@Getter
@NoArgsConstructor
@CacheConfig(cacheNames = "test1")
public class TestService {
	private int value = 0;

	@Cacheable
	public int add() {
		return ++value;
	}

	@CacheEvict
	public void reset() {
		this.value = 0;
	}
}
