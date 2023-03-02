package io.jutil.springeasy.spring.convert;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */

import io.jutil.springeasy.core.dict.Status;
import io.jutil.springeasy.spring.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

@SpringBootTest(classes = Application.class)
class StatusTest {
    @Autowired
    ConversionService conversionService;


    @Test
    void testString() {
        Assertions.assertEquals(Status.ACTIVE, conversionService.convert("正常", Status.class));
        Assertions.assertEquals(Status.INACTIVE, conversionService.convert("作废", Status.class));
    }

    @Test
    void testNumber() {
        Assertions.assertEquals(Status.ACTIVE, conversionService.convert("0", Status.class));
        Assertions.assertEquals(Status.INACTIVE, conversionService.convert("1", Status.class));

        Assertions.assertEquals(Status.ACTIVE, conversionService.convert(0, Status.class));
        Assertions.assertEquals(Status.INACTIVE, conversionService.convert(1, Status.class));
    }

}
