package io.jutil.springeasy.core.cache;

import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 1.0 2021-08-27
 */
@Slf4j
class RemovalListenerTest implements RemovalListener<Integer, Integer> {
	private Cache<Integer, Integer> cache;

	private Integer key;
	private Integer value;
	private RemovalCause cause;


	@BeforeEach
	void beforeEach() {
		cache = Cache.builder()
				.expireAfterAccess(100, TimeUnit.MILLISECONDS)
				.removalListener(this)
				.build();
	}

	@Test
	void test() {
		cache.put(1, 1);
		Assertions.assertEquals(1, cache.get(1).intValue());
		WaitUtil.sleep(200);
		Assertions.assertNull(cache.get(1));

	}

	@Override
	public void onRemoval(Integer key, Integer value, RemovalCause cause) {
		this.key = key;
		this.value = value;
		this.cause = cause;
		log.info("onRemoval, key: {}, value: {}, cause: {}", key, value, cause);
	}
}
