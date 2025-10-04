package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jin Zheng
 * @since 2025-09-29
 */
@Slf4j
public class JsonEquals {
	private JsonEquals() {
	}

	public static boolean equals(JSONObject object1, JSONObject object2) {
		if (object1 == object2) {
			return true;
		}
		if (object1 == null || object2 == null) {
			log.warn("JSONObject not equals, object1: {}, object2: {}", object1, object2);
			return false;
		}
		if (object1.size() != object2.size()) {
			log.warn("JSONObject not equals, object1.size(): {}, object2.size(): {}",
					object1.size(), object2.size());
			return false;
		}

		for (var entry : object1.entrySet()) {
			if (!object2.containsKey(entry.getKey())) {
				log.warn("JSONObject not equals, object1 exists key: {}, object2 not exists",
						entry.getKey());
				return false;
			}
			var value2 = object2.get(entry.getKey());
			if (!equalsValue(entry.getValue(), value2)) {
				log.warn("JSONObject not equals, object1[{}]: {}, object2[{}]: {}",
						entry.getKey(), entry.getValue(), entry.getKey(), value2);
				return false;
			}
		}
		return true;
	}

	public static boolean equals(JSONArray array1, JSONArray array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1 == null || array2 == null) {
			log.warn("JSONArray not equals, array1: {}, array2: {}", array1, array2);
			return false;
		}
		if (array1.size() != array2.size()) {
			log.warn("JSONArray not equals, array1.size(): {}, array2.size(): {}",
					array1.size(), array2.size());
			return false;
		}

		for (int i = 0; i < array1.size(); i++) {
			var value1 = array1.get(i);
			var value2 = array2.get(i);
			if (!equalsValue(value1, value2)) {
				log.warn("JSONArray not equals, array1[{}]: {}, array2[{}]: {}", i, value1,
						i, value2);
				return false;
			}
		}
		return true;
	}

	private static boolean equalsValue(Object value1, Object value2) {
		if (value1 == value2) {
			return true;
		}
		if (value1 == null || value2 == null) {
			return false;
		}
		return switch (value1) {
			case Dict dict1 when value2 instanceof Number number2 -> dict1.getCode() == number2.intValue();
			case Number number1 when value2 instanceof Dict dict2 -> number1.intValue() == dict2.getCode();
			case Number number1 when value2 instanceof Number number2 -> equalsNumber(number1, number2);
			case LocalDateTime date1 when value2 instanceof String date2 ->
					date2.equals(DateUtil.DATE_TIME_FORMATTER.format(date1));
			case String date1 when value2 instanceof LocalDateTime date2 ->
					date1.equals(DateUtil.DATE_TIME_FORMATTER.format(date2));
			case JSONObject object1 when value2 instanceof JSONObject object2 -> equals(object1, object2);
			case JSONArray array1 when value2 instanceof JSONArray array2 -> equals(array1, array2);
			default -> Objects.equals(value1, value2);
		};
	}

	private static boolean equalsNumber(Number number1, Number number2) {
		var b1 = new BigDecimal(number1.toString());
		var b2 = new BigDecimal(number2.toString());
		return b1.compareTo(b2) == 0;
	}
}
