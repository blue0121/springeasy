package io.jutil.springeasy.core.codec.json;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * @author Jin Zheng
 * @since 2025-12-28
 */
public class TemporalAccessorObjectWriter implements ObjectWriter<TemporalAccessor> {
	public static final TemporalAccessorObjectWriter INSTANCE = new TemporalAccessorObjectWriter();

	@Override
	public void write(JSONWriter writer, Object object, Object fieldName, Type fieldType, long features) {
		switch (object) {
			case null -> writer.writeNull();
			case LocalDateTime dateTime -> writer.writeString(Formatter.DATE_TIME.format(dateTime));
			case LocalDate date -> writer.writeString(Formatter.DATE.format(date));
			case LocalTime time -> writer.writeString(Formatter.TIME.format(time));
			default -> writer.writeString(object.toString());
		}
	}
}
