package io.jutil.springeasy.core.convert;

import org.junit.jupiter.api.Assertions;

import java.time.format.DateTimeFormatter;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
abstract class ConverterTest {
    protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static final String DATE_FORMAT = "yyyy-MM-dd";
    protected static final String TIME_FORMAT = "HH:mm:ss";

    protected static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    protected static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(TIME_FORMAT);

    protected ConvertService service = ConvertServiceFactory.getConvertService();


    protected void verifyConvert(Class<?> sourceType, Class<?> targetType) {
        Assertions.assertTrue(service.canConvert(sourceType, targetType));
    }
}
