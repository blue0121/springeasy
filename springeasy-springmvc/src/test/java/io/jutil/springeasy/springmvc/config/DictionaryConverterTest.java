package io.jutil.springeasy.springmvc.config;

import io.jutil.springeasy.core.dict.Status;
import io.jutil.springeasy.springmvc.BaseTest;
import io.jutil.springeasy.springmvc.controller.DictionaryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-01-28
 */
@SuppressWarnings("java:S5976")
class DictionaryConverterTest extends BaseTest {
    private Status status = Status.ACTIVE;

    @BeforeEach
    void beforeEach() {
        this.init();
    }

    @Test
    void testString() {
        // NOSONAR
        var json = "{\"name\":\"blue\",\"status\":\"正常\"}";
        this.verify(json);
    }

    private void verify(String json) {
        var url = this.buildUrl("/dictionary");
        var response = httpTemplate.post(url, json);
        Assertions.assertEquals(200, response.getStatusCode());

        var body = response.convertTo(DictionaryResponse.class);
        Assertions.assertEquals("blue", body.getName());
        Assertions.assertEquals(status, body.getStatus());
    }

    @Test
    void testNumber1() {
        // NOSONAR
        var json = "{\"name\":\"blue\",\"status\":\"0\"}";
        this.verify(json);
    }

    @Test
    void testNumber2() {
        // NOSONAR
        var json = "{\"name\":\"blue\",\"status\":0}";
        this.verify(json);
    }

}
