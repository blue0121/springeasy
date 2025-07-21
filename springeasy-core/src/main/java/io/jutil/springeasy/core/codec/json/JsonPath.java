package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;

/**
 * @author Jin Zheng
 * @since 2025-07-14
 */
public class JsonPath {
	private JsonPath() {
	}

	private static Object get(Object object, String path) {
		var jsonPath = JSONPath.of(path);
		return jsonPath.eval(object);
	}

	public static JSONObject getJsonObject(Object object, String path) {
		return (JSONObject) get(object, path);
	}

	public static JSONArray getJsonArray(Object object, String path) {
		return (JSONArray) get(object, path);
	}

	public static String getString(Object object, String path) {
		return (String) get(object, path);
	}
}
