package io.jutil.springeasy.internal.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.time.*;
import java.time.temporal.TemporalAccessor;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class StringToLocalDateConverterFactory implements ConverterFactory<String, TemporalAccessor> {

	@Override
	public <T extends TemporalAccessor> Converter<String, T> getConverter(Class<T> targetType) {
		if (DateTimeConst.TEMPORAL_SET.contains(targetType)) {
			return new DateToLocalDate<>(targetType);
		}

		return null;
	}

	private class DateToLocalDate<T extends TemporalAccessor> implements Converter<String, T> {
		private final Class<T> clazz;

		public DateToLocalDate(Class<T> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(String source) {
			var dateTime = this.parse(source);
			if (dateTime == null) {
				return null;
			}

			if (clazz == LocalDate.class) {
				return (T) dateTime.toLocalDate();
			}
			if (clazz == LocalTime.class) {
				return (T) dateTime.toLocalTime();
			}
			if (clazz == Instant.class) {
				// TODO: to be fix time zone
				return (T) dateTime.atZone(ZoneId.systemDefault())
						.toInstant();
			}

			return (T) dateTime;
		}

		private LocalDateTime parse(String source) {
			if (source == null || source.isEmpty()) {
				return null;
			}

			if (DateTimeConst.DATE_TIME_REGEX.matcher(source).matches()) {
				return LocalDateTime.parse(source, DateTimeConst.DATE_TIME_FORMATTER);
			}
			if (DateTimeConst.DATE_REGEX.matcher(source).matches()) {
				return LocalDate.parse(source).atStartOfDay();
			}
			if (DateTimeConst.TIME_REGEX.matcher(source).matches()) {
				var time = LocalTime.parse(source);
				return LocalDateTime.of(LocalDate.EPOCH, time);
			}

			return null;
		}
	}
}
