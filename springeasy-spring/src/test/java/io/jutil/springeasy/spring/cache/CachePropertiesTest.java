package io.jutil.springeasy.spring.cache;

import io.jutil.springeasy.spring.Application;
import io.jutil.springeasy.spring.config.cache.CacheProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-05-21
 */
@ActiveProfiles("cache")
@SpringBootTest(classes = Application.class)
class CachePropertiesTest {
	@Autowired
	CacheProperties prop;

	@Test
	void test() {
		Assertions.assertEquals("local", prop.getType());
		Assertions.assertEquals(60, prop.getExpireSec());
		Assertions.assertEquals(10000, prop.getMaxSize());
		Assertions.assertEquals(300, prop.getExpireOtherSec());
		Assertions.assertEquals(50000, prop.getMaxOtherSize());
	}

	@CsvSource({"test1,10,50,1000,5000", "test2,60,300,10000,50000"})
	@ParameterizedTest
	void testItem(String id, int expire, int expireOther, int maxSize, int maxOtherSize) {
		var item = prop.getItem(id);
		Assertions.assertNotNull(item);
		Assertions.assertEquals(expire, item.getExpireSec());
		Assertions.assertEquals(expireOther, item.getExpireOtherSec());
		Assertions.assertEquals(maxSize, item.getMaxSize());
		Assertions.assertEquals(maxOtherSize, item.getMaxOtherSize());
	}
}
