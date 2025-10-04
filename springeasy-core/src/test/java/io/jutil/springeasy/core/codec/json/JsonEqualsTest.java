package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-09-29
 */
class JsonEqualsTest {

	final LocalDateTime now = DateUtil.now();

	@Test
	void testObject1() {
		var object1 = JSONObject.of("id", 1, "name", "blue",
				"price", 10.0, "status", true, "date", now);
		var object2 = JSONObject.of("id", 1L, "name", "blue",
				"price", 10, "status", true, "date", DateUtil.DATE_TIME_FORMATTER.format(now));
		Assertions.assertTrue(JsonEquals.equals(object1, object2));
	}

	@Test
	void testArray1() {
		var array1 = JSONArray.of(1, 10.0, "name", true);
		var array2 = JSONArray.of(1L, 10, "name", true);
		Assertions.assertTrue(JsonEquals.equals(array1, array2));
	}
}
