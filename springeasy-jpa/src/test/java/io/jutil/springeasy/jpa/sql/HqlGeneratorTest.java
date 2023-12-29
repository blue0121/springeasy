package io.jutil.springeasy.jpa.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-09
 */
class HqlGeneratorTest {

    Map<String, String> args = new LinkedHashMap<>();

    HqlGeneratorTest() {
        args.put("k1", "v1");
        args.put("k2", "v2");
    }

    @Test
    void testWhere() {
        var where = HqlGenerator.where(args);
        Assertions.assertEquals("where k1=:k1 and k2=:k2", where);
    }

    @Test
    void testUpdateSet() {
        var updateSet = HqlGenerator.updateSet(args);
        Assertions.assertEquals("k1=:k1,k2=:k2", updateSet);
    }
}
