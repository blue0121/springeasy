package io.jutil.springeasy.core.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-21
 */
class CacheLoaderTest {
    private Cache<Integer, Integer> cache;


    @BeforeEach
    void beforeEach() {
        cache = Cache.builder().build(set -> {
            Map<Integer, Integer> map = new HashMap<>();
            for (var k : set) {
                map.put(k, k * 10);
            }
            return map;
        });
    }

    @Test
    void testGet() {
        Assertions.assertEquals(10, cache.get(1));
        Assertions.assertEquals(100, cache.get(10));
        cache.put(10, 10);
        Assertions.assertEquals(10, cache.get(10));
    }

}
