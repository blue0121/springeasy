package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import jakarta.validation.ValidationException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-07-14
 */
public class EnumObjectReader<T extends Enum<T>> implements ObjectReader<T> {
	private final boolean isDict;

	private final Map<Integer, T> ordinalMap = new HashMap<>();
	private final Map<Integer, T> codeMap = new HashMap<>();
	private final Map<String, T> nameMap = new HashMap<>();

	public EnumObjectReader(Class<T> clazz) {
		this.isDict = Dict.class.isAssignableFrom(clazz);
		for (var entry : clazz.getEnumConstants()) {
			ordinalMap.put(entry.ordinal(), entry);
			nameMap.put(entry.name(), entry);
			if (entry instanceof Dict dict) {
				codeMap.put(dict.getCode(), entry);
			}
		}
	}

	@Override
	public T readObject(JSONReader reader, Type fieldType, Object fieldName, long features) {
		T target = null;
		if (reader.isInt()) {
			var code = reader.readInt32Value();
			if (isDict) {
				target = codeMap.get(code);
			} else {
				target = ordinalMap.get(code);
			}
			if (target == null) {
				throw new ValidationException("无效的枚举值: " + code);
			}
		} else if (reader.isString()) {
			var name = reader.readString();
			target = nameMap.get(name);
			if (target == null) {
				throw new ValidationException("无效的枚举值: " + name);
			}
		}

		return target;
	}
}
