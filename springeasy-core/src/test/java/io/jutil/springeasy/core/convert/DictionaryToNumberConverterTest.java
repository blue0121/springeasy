package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class DictionaryToNumberConverterTest extends ConverterTest {

    @Test
    void testToInt() {
        this.verifyConvert(Dictionary.class, Integer.class);
        var val = service.convert(Status.ACTIVE, Integer.class);
        Assertions.assertEquals(0, val);
    }

    @Test
    void testToLong() {
        this.verifyConvert(Dictionary.class, Long.class);
        var val = service.convert(Status.ACTIVE, Long.class);
        Assertions.assertEquals(0L, val);
    }
}
