package io.jutil.springeasy.core.convert;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class DateTimeConst {
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String TIME_FORMAT = "HH:mm:ss";

    static final Pattern DATE_TIME_REGEX =
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    static final Pattern DATE_REGEX =
            Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    static final Pattern TIME_REGEX =
            Pattern.compile("^\\d{2}:\\d{2}:\\d{2}$");

    static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    static final Set<Class<?>> TEMPORAL_SET = Set.of(Instant.class, LocalDate.class,
            LocalTime.class, LocalDateTime.class);
    static final Set<Class<?>> DATE_SET = Set.of(Date.class, java.sql.Date.class,
            Time.class, Timestamp.class);

    private DateTimeConst() {
    }

    @SuppressWarnings("unchecked")
    static <T extends Date> T toDate(Date date, Class<T> clazz) {
        if (date == null) {
            return null;
        }

        if (clazz == java.sql.Date.class) {
            return (T) new java.sql.Date(date.getTime());
        }
        if (clazz == Time.class) {
            return (T) new Time(date.getTime());
        }
        if (clazz == Timestamp.class) {
            return (T) new Timestamp(date.getTime());
        }

        return (T) date;
    }
}
