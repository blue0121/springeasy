package io.jutil.springeasy.core.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-21
 */
class CacheTest {
    private Cache<Integer, Integer> cache;


	@BeforeEach
	void beforeEach() {
	    cache = Cache.builder().build();
    }

    @Test
    void test() {
        Assertions.assertNull(cache.get(1));
        cache.put(1, 10);
        Assertions.assertEquals(10, cache.get(1));
        cache.clear();
        Assertions.assertNull(cache.get(1));
    }

}
