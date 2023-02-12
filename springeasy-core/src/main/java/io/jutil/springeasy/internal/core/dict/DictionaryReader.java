package io.jutil.springeasy.internal.core.dict;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import io.jutil.springeasy.core.dict.Dictionary;
import io.jutil.springeasy.core.dict.DictionaryCache;
import io.jutil.springeasy.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@Slf4j
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
            return (T) cache.getFromName(clazz, val);
        } else if (reader.isObject()) {
			var map = reader.readObject();
			var index = (Integer) map.get("index");
	        return (T) cache.getFromIndex(clazz, index);
        }
		return null;
	}
}
