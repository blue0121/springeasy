package io.jutil.springeasy.core.dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-03
 */
class ColorTest {

    @Test
    void test() {
        var map = Color.getStringMap();
        Assertions.assertFalse(map.isEmpty());

        Assertions.assertEquals("主要", Color.PRIMARY.getName());
    }
}
