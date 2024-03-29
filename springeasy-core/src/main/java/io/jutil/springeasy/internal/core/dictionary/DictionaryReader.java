package io.jutil.springeasy.internal.core.dictionary;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.DictionaryCache;
import io.jutil.springeasy.core.util.NumberUtil;

import java.lang.reflect.Type;

/**
 * @author Jin Zheng
 * @since 2023-05-28
 */
public class DictionaryReader<T extends Dictionary> implements ObjectReader<T> {
	private final Class<? extends Dictionary> clazz;
	private final DictionaryCache cache = DictionaryCache.getInstance();

	public DictionaryReader(Class<? extends Dictionary> clazz) {
		this.clazz = clazz;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T readObject(JSONReader reader, Type fieldType, Object fieldName, long features) {
		if (reader.isInt()) {
			var val = reader.readInt32();
			return (T) cache.getFromIndex(clazz, val);
		} else if (reader.isString()) {
			var val = reader.readString();
			if (NumberUtil.isInteger(val)) {
				return (T) cache.getFromIndex(clazz, Integer.valueOf(val));
			}
			var dict = cache.getFromName(clazz, val);
			if (dict != null) {
				return (T) dict;
			}
			return (T) cache.getFromLabel(clazz, val);
		} else if (reader.isObject()) {
			var map = reader.readObject();
			var index = (Integer) map.get("index");
			return (T) cache.getFromIndex(clazz, index);
		}
		return null;
	}

}
