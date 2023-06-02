package io.jutil.springeasy.internal.core.dictionary;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;
import io.jutil.springeasy.core.dictionary.Dictionary;

import java.lang.reflect.Type;

/**
 * @author Jin Zheng
 * @since 2023-05-28
 */
@SuppressWarnings("java:S6548")
public class DictionaryWriter implements ObjectWriter<Object> {
	public static final DictionaryWriter INSTANCE = new DictionaryWriter();

	@Override
	public void write(JSONWriter writer, Object object, Object fieldName,
	                  Type fieldType, long features) {
		if (object == null) {
			writer.writeNull();
			return;
		}
		if (!(object instanceof Dictionary dictionary)) {
			throw new UnsupportedOperationException("Unsupported type: "
					+ object.getClass().getName());
		}

		var json = new JSONObject();
		json.put("index", dictionary.getIndex());
		json.put("name", dictionary.getName());
		json.put("label", dictionary.getLabel());
		json.put("color", dictionary.getColor().toString());
		writer.write(json);
	}
}
