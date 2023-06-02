package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class StringToDateConverterTest extends ConverterTest {

    @Test
    void testToDate() {
        this.verifyConvert(String.class, Date.class);
        var str = "2023-01-01 12:01:30";
        var date = service.convert(str, Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(str, DateUtil.format(date, DATE_TIME_FORMAT));
    }

    @Test
    void testToSqlDate() {
        this.verifyConvert(String.class, java.sql.Date.class);
        var str = "2023-01-01";
        var date = service.convert(str, java.sql.Date.class);
        Assertions.assertNotNull(date);
        Assertions.assertEquals(str, date.toString());
    }

    @Test
    void testToSqlTime() {
        this.verifyConvert(String.class, Time.class);
        var str = "12:01:30";
        var time = service.convert(str, Time.class);
        Assertions.assertNotNull(time);
        Assertions.assertEquals(str, time.toString());
    }

    @Test
    void testToSqlTimestamp() {
        this.verifyConvert(String.class, Timestamp.class);
        var str = "2023-01-01 12:01:30";
        var timestamp = service.convert(str, Timestamp.class);
        Assertions.assertNotNull(timestamp);
        Assertions.assertEquals(str, DateUtil.format(timestamp, DATE_TIME_FORMAT));
    }

}
