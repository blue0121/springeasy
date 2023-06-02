package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class LocalDateToDateConverterTest extends ConverterTest {

    @Test
    void testLocalDateToDate() {
        this.verifyConvert(LocalDate.class, Date.class);
        var localDate = LocalDate.now();
        var date = service.convert(localDate, Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(localDate.toString(),
                DateUtil.format(date, DATE_FORMAT));
    }

    @Test
    void testLocalTimeToDate() {
        this.verifyConvert(LocalTime.class, Date.class);
        var localTime = LocalTime.now();
        var date = service.convert(localTime, Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(localTime.format(TIME_FORMATTER),
                DateUtil.format(date, TIME_FORMAT));
    }

    @Test
    void testLocalDateTimeToDate() {
        this.verifyConvert(LocalDateTime.class, Date.class);
        var localDateTime = LocalDateTime.now();
        var date = service.convert(localDateTime, Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(localDateTime.format(DATE_TIME_FORMATTER),
                DateUtil.format(date, DATE_TIME_FORMAT));
    }

    @Test
    void testInstantToDate() {
        this.verifyConvert(Instant.class, Date.class);
        var instant = Instant.now();
        var date = service.convert(instant, Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(instant.toEpochMilli(), date.getTime());
    }

    @Test
    void testInstantToSqlDate() {
        this.verifyConvert(Instant.class, java.sql.Date.class);
        var instant = Instant.now();
        var date = service.convert(instant, java.sql.Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(instant.toEpochMilli(), date.getTime());
    }

    @Test
    void testInstantToSqlTime() {
        this.verifyConvert(Instant.class, Time.class);
        var instant = Instant.now();
        var date = service.convert(instant, Time.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(instant.toEpochMilli(), date.getTime());
    }

    @Test
    void testInstantToTimestamp() {
        this.verifyConvert(Instant.class, Timestamp.class);
        var instant = Instant.now();
        var date = service.convert(instant, Timestamp.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(instant.toEpochMilli(), date.getTime());
    }
}
