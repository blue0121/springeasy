package io.jutil.springeasy.internal.core.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-02
 */
public class LocalDateToDateConverterFactory implements ConverterFactory<TemporalAccessor, Date> {

	@Override
	public <T extends Date> Converter<TemporalAccessor, T> getConverter(Class<T> targetType) {
		if (DateTimeConst.DATE_SET.contains(targetType)) {
			return new LocalDateToDate<>(targetType);
		}
		return null;
	}

	private class LocalDateToDate<T extends Date> implements Converter<TemporalAccessor, T> {
		private final Class<T> clazz;

		public LocalDateToDate(Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T convert(TemporalAccessor source) {
			var date = this.toDate(source);
			return DateTimeConst.toDate(date, clazz);
		}

		private Date toDate(TemporalAccessor source) {
			if (source instanceof Instant instant) {
				return Date.from(instant);
			}

			if (source instanceof LocalDate date) {
				return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			}

			if (source instanceof LocalTime time) {
				return Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
			}

			if (source instanceof LocalDateTime dateTIme) {
				return Date.from(dateTIme.atZone(ZoneId.systemDefault()).toInstant());
			}
			return null;
		}
	}
}
