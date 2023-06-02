package io.jutil.springeasy.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class StringToLocalDateConverterTest extends ConverterTest {

    @Test
    void testToLocalDate() {
        this.verifyConvert(String.class, LocalDate.class);
        var str = "2023-01-01";
        var localDate = service.convert(str, LocalDate.class);
        Assertions.assertNotNull(localDate);
        Assertions.assertEquals(str, localDate.toString());
    }

    @Test
    void testToLocalTime() {
        this.verifyConvert(String.class, LocalTime.class);
        var str = "12:01:30";
        var localTime = service.convert(str, LocalTime.class);
        Assertions.assertNotNull(localTime);
        Assertions.assertEquals(str, localTime.toString());
    }

    @Test
    void testToLocalDateTime() {
        this.verifyConvert(String.class, LocalDateTime.class);
        var str = "2023-01-01 12:01:30";
        var localDateTime = service.convert(str, LocalDateTime.class);
        Assertions.assertNotNull(localDateTime);
        Assertions.assertEquals(str, localDateTime.format(DATE_TIME_FORMATTER));
    }

    @Disabled
    @Test
    void testToInstant() {
        this.verifyConvert(String.class, Instant.class);
        var str = "2023-01-01 12:01:30";
        var instant = service.convert(str, Instant.class);
        Assertions.assertNotNull(instant);
        Assertions.assertEquals("2023-01-01T12:01:30Z", instant.toString());
    }

}
