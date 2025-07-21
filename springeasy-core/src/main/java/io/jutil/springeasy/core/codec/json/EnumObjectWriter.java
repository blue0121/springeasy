package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;

/**
 * @author Jin Zheng
 * @since 2025-07-15
 */
public class EnumObjectWriter implements ObjectWriter<Dict> {
	public static final EnumObjectWriter INSTANCE = new EnumObjectWriter();

	@Override
	public void write(JSONWriter writer, Object object, Object fieldName, Type fieldType, long features) {
		if (object == null) {
			writer.writeNull();
			return;
		}
		var dict = (Dict) object;
		writer.writeInt32(dict.getCode());
	}
}
