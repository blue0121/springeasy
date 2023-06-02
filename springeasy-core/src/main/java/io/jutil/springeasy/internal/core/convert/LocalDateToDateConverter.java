package io.jutil.springeasy.internal.core.convert;

import org.springframework.core.convert.converter.Converter;

import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
public class LocalDateToDateConverter implements Converter<TemporalAccessor, Date> {

    @Override
    public Date convert(TemporalAccessor source) {
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

        throw new IllegalArgumentException("Unsupported type: " + source.getClass().getName());
    }
}
