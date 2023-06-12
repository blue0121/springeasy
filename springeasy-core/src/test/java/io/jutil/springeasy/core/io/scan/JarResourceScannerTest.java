package io.jutil.springeasy.core.io.scan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

/**
 * @author Jin Zheng
 * @since 2023-06-12
 */
class JarResourceScannerTest {
    @Test
    void testScanPackage() {
        var handler = new FileHandler();
        ResourceScannerFacade.scanPackage("org.slf4j", handler);

        Assertions.assertTrue(handler.nameList.size() > 1);
        Assertions.assertTrue(handler.classList.size() > 1);
        Assertions.assertTrue(handler.nameList.contains("Logger.class"));
        Assertions.assertTrue(handler.classList.contains(Logger.class));
    }
}
