package io.jutil.springeasy.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Jin Zheng
 * @since 1.0 2021-08-27
 */
@Slf4j
class CaffeineTest {

	private String key = "key";
	private String key2 = "key2";


    @Test
    void testRemove() {
	    Cache<String, String> cache = Caffeine.newBuilder()
			    .expireAfterAccess(Duration.ofMillis(10))
			    .removalListener((k, v, cause) -> {
					log.info("removalListener, k: {}, v: {}, cause: {}", k, v, cause);
			    }).evictionListener((k, v, cause) -> {
				    log.info("evictionListener, k: {}, v: {}, cause: {}", k, v, cause);
			    })
			    .build();
		cache.put(key, key);
		cache.put(key2, key2);
		cache.invalidate(key2);
	    WaitUtil.sleep(20);
	    Assertions.assertNull(cache.getIfPresent(key));
    }

}
