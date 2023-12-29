package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.util.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-02
 */
public class StringToDateConverterFactory implements ConverterFactory<String, Date> {

	@Override
	public <T extends Date> Converter<String, T> getConverter(Class<T> targetType) {
		if (DateTimeConst.DATE_SET.contains(targetType)) {
			return new StringToDate(targetType);
		}

		return null;
	}

	private class StringToDate<T extends Date> implements Converter<String, T> {
		private final Class<T> clazz;

		public StringToDate(Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T convert(String source) {
			var date = this.toDate(source);
			return DateTimeConst.toDate(date, clazz);
		}

		private Date toDate(String source) {
			if (source == null || source.isEmpty()) {
				return null;
			}
			if (DateTimeConst.DATE_TIME_REGEX.matcher(source).matches()) {
				return DateUtil.parse(source, DateTimeConst.DATE_TIME_FORMAT);
			}
			if (DateTimeConst.DATE_REGEX.matcher(source).matches()) {
				return DateUtil.parse(source, DateTimeConst.DATE_FORMAT);
			}
			if (DateTimeConst.TIME_REGEX.matcher(source).matches()) {
				return DateUtil.parse(source, DateTimeConst.TIME_FORMAT);
			}

			return null;
		}
	}


}
