package io.jutil.springeasy.core.convert;

import io.jutil.springeasy.core.dictionary.Dictionary;
import io.jutil.springeasy.core.dictionary.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-06-01
 */
class DictionaryToStringConverterTest extends ConverterTest {

    @Test
    void testToString() {
        this.verifyConvert(Dictionary.class, String.class);
        var val = service.convert(Status.ACTIVE, String.class);
        Assertions.assertEquals("ACTIVE", val);
    }

}
