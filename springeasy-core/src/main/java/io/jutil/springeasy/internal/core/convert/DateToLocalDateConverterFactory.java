package io.jutil.springeasy.internal.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class DateToLocalDateConverterFactory implements ConverterFactory<Date, TemporalAccessor> {

	@Override
	public <T extends TemporalAccessor> Converter<Date, T> getConverter(Class<T> targetType) {
		if (DateTimeConst.TEMPORAL_SET.contains(targetType)) {
			return new DateToLocalDate<>(targetType);
		}

		return null;
	}

	private class DateToLocalDate<T extends TemporalAccessor> implements Converter<Date, T> {
		private final Class<T> clazz;

		public DateToLocalDate(Class<T> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convert(Date source) {
			var instant = source.toInstant();
			if (clazz == Instant.class) {
				return (T) instant;
			}

			var zoned = instant.atZone(ZoneId.systemDefault());

			if (clazz == LocalDate.class) {
				return (T) zoned.toLocalDate();
			}

			if (clazz == LocalTime.class) {
				return (T) zoned.toLocalTime();
			}

			if (clazz == LocalDateTime.class) {
				return (T) zoned.toLocalDateTime();
			}

			return null;
		}
	}
}
