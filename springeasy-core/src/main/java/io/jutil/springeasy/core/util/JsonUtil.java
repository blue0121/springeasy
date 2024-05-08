package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 1.0 2019-07-26
 */
@Slf4j
public class JsonUtil {
	private static final String BASE_FILTER = "io.jutil.springeasy";

	private static JSONWriter.Feature[] WRITER = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteClassName,
			JSONWriter.Feature.WriteEnumUsingOrdinal,
			JSONWriter.Feature.WriteLongAsString
	};

	private static final JSONWriter.Feature[] OUTPUT = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteEnumUsingOrdinal,
			JSONWriter.Feature.WriteLongAsString
	};

	public static Filter autoedTypeFilter = JSONReader.autoTypeFilter(BASE_FILTER);

	private JsonUtil() {
	}

	public static void registerAutoType(String...names) {
		List<String> nameList = new ArrayList<>();
		nameList.add(BASE_FILTER);
		for (var name : names) {
			nameList.add(name);
		}
		autoedTypeFilter = JSONReader.autoTypeFilter(nameList.toArray(new String[0]));
	}

	public static byte[] toBytes(Object object) {
		if (object == null) {
			return new byte[0];
		}

		if (object instanceof byte[] bytes) {
			return bytes;
		}

		if (object instanceof CharSequence str) {
			return str.toString().getBytes(StandardCharsets.UTF_8);
		}

		return JSON.toJSONBytes(object, WRITER);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		return (T) JSON.parseObject(bytes, Object.class, autoedTypeFilter);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		if (clazz == byte[].class) {
			return (T) bytes;
		}

		if (clazz == String.class) {
			return (T) new String(bytes, StandardCharsets.UTF_8);
		}

		return JSON.parseObject(bytes, clazz, autoedTypeFilter);
	}

	public static String output(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof byte[] bytes) {
			return String.format("{%d byte array}", bytes.length);
		}

		if (object instanceof CharSequence str) {
			return str.toString();
		}

		return JSON.toJSONString(object, OUTPUT);
	}

	public static String toString(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof byte[] bytes) {
			return new String(bytes, StandardCharsets.UTF_8);
		}

		if (object instanceof CharSequence str) {
			return str.toString();
		}

		return JSON.toJSONString(object, WRITER);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromString(String json) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		return (T) JSON.parseObject(json, Object.class, autoedTypeFilter);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromString(String json, Class<T> clazz) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		if (clazz == String.class) {
			return (T) json;
		}

		return JSON.parseObject(json, clazz, autoedTypeFilter);
	}

	public static void removeType(Object object) {
		if (object instanceof JSONObject obj) {
			obj.remove("@type");
			for (var attr : obj.entrySet()) {
				removeType(attr.getValue());
			}
		} else if (object instanceof JSONArray array) {
			for (int i = 0; i < array.size(); i++) {
				removeType(array.getJSONObject(i));
			}
		}
	}

	public static String toLog(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		if (!text.contains("\n")) {
			return text;
		}

		if (JSON.isValidObject(text)) {
			return JSON.parseObject(text).toString();
		}
		if (JSON.isValidArray(text)) {
			return JSON.parseArray(text).toString();
		}

		return text;
	}
}
