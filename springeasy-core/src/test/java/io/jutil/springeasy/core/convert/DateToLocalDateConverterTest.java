package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class DateToLocalDateConverterTest extends ConverterTest {

    @Test
    void testDateToLocalDate() {
        this.verifyConvert(Date.class, LocalDate.class);
        var date = new Date();
        var localDate = service.convert(date, LocalDate.class);
        Assertions.assertNotNull(localDate);
        Assertions.assertEquals(DateUtil.format(date, DATE_FORMAT),
                localDate.toString());
    }

    @Test
    void testDateToLocalTime() {
        this.verifyConvert(Date.class, LocalTime.class);
        var date = new Date();
        var localTime = service.convert(date, LocalTime.class);
        Assertions.assertNotNull(localTime);
        Assertions.assertEquals(DateUtil.format(date, TIME_FORMAT),
                localTime.format(TIME_FORMATTER));
    }

    @Test
    void testDateToLocalDateTime() {
        this.verifyConvert(Date.class, LocalDateTime.class);
        var date = new Date();
        var localDateTime = service.convert(date, LocalDateTime.class);
        Assertions.assertNotNull(localDateTime);
        Assertions.assertEquals(DateUtil.format(date, DATE_TIME_FORMAT),
                localDateTime.format(DATE_TIME_FORMATTER));
    }


    @Test
    void testTimestampToLocalDateTime() {
        this.verifyConvert(Timestamp.class, LocalDateTime.class);
        var timestamp = new Timestamp(System.currentTimeMillis());
        var localDateTime = service.convert(timestamp, LocalDateTime.class);
        Assertions.assertNotNull(localDateTime);
        Assertions.assertEquals(DateUtil.format(timestamp, DATE_TIME_FORMAT),
                localDateTime.format(DATE_TIME_FORMATTER));
    }

}
