package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2025-07-14
 */
class JsonPathTest {

	@Test
	void testGet() {
		var obj = new JSONObject();
		var array = new JSONArray();
		var json = JSONObject.of("k1", "v1", "k2", obj, "k3", array);
		Assertions.assertEquals("v1", JsonPath.getString(json, "$.k1"));
		Assertions.assertEquals(obj, JsonPath.getJsonObject(json, "$.k2"));
		Assertions.assertEquals(array, JsonPath.getJsonArray(json, "$.k3"));
	}
}
