package io.jutil.springeasy.core.cache;

import io.jutil.springeasy.core.util.WaitUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-23
 */
class CacheExpiryTest {
    private Cache<Integer, Integer> cache;

	@BeforeEach
	void beforeEach() {
	    this.cache = Cache.builder().expire(new Expiry<Integer, Integer>() {

            @Override
            public long expireAfterCreate(Integer key, Integer value, long currentTimeMillis) {
                return 200;
            }

            @Override
            public long expireAfterUpdate(Integer key, Integer value, long currentTimeMillis, long currentDurationMillis) {
                return 200;
            }

            @Override
            public long expireAfterRead(Integer key, Integer value, long currentTimeMillis, long currentDurationMillis) {
                return currentDurationMillis;
            }
        }).build();
    }

    @Test
    void testExpiry() {
	    cache.put(1, 1);
        Assertions.assertEquals(1, cache.get(1));
        WaitUtil.sleep(500);
        Assertions.assertNull(cache.get(1));
        cache.put(1, 10);
        Assertions.assertEquals(10, cache.get(1));
        WaitUtil.sleep(100);
        Assertions.assertEquals(10, cache.get(1));
        WaitUtil.sleep(200);
        Assertions.assertNull(cache.get(1));
    }

}
