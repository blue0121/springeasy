package io.jutil.springeasy.core.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 1.0 2019-07-26
 */
@Slf4j
public class JsonUtil {
	private static final String BASE_FILTER = "io.jutil";

	@SuppressWarnings("java:S2386")
	public static final JSONWriter.Feature[] WRITER = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteClassName,
			JSONWriter.Feature.WriteEnumUsingOrdinal,
			JSONWriter.Feature.WriteLongAsString
	};

	@SuppressWarnings("java:S2386")
	public static final JSONWriter.Feature[] OUTPUT = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteEnumUsingOrdinal,
			JSONWriter.Feature.WriteLongAsString
	};

	private static Filter autoedTypeFilter = JSONReader.autoTypeFilter(BASE_FILTER);

	private JsonUtil() {
	}

	public static Filter getAutoedTypeFilter() {
		return autoedTypeFilter;
	}

	public static void registerAutoType(String...names) {
		if (names == null || names.length == 0) {
			return;
		}

		String[] newNames = new String[names.length + 1];
		names[0] = BASE_FILTER;
		System.arraycopy(names, 0, newNames, 1, names.length);
		autoedTypeFilter = JSONReader.autoTypeFilter(newNames);
	}

	public static byte[] toBytes(Object object) {
		if (object == null) {
			return new byte[0];
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

	public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		return JSON.parseObject(bytes, clazz, autoedTypeFilter);
	}

	@SuppressWarnings("java:S1845")
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
		return JSON.toJSONString(object, WRITER);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromString(String json) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		return (T) JSON.parseObject(json, Object.class, autoedTypeFilter);
	}

	public static <T> T fromString(String json, Class<T> clazz) {
		if (json == null || json.isEmpty()) {
			return null;
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
