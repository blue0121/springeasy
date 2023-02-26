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
public class DictionaryConverterTest extends BaseTest {
    private Status status = Status.ACTIVE;

    @BeforeEach
    public void beforeEach() {
        this.init();
    }

    @Test
    public void testString() {
        var url = this.buildUrl("/dictionary");
        var json = "{\"name\":\"blue\",\"status\":\"正常\"}";
        var response = httpTemplate.post(url, json);
        Assertions.assertEquals(200, response.getStatusCode());

        var body = response.convertTo(DictionaryResponse.class);
        Assertions.assertEquals("blue", body.getName());
        Assertions.assertEquals(status, body.getStatus());
    }

    @Test
    public void testNumber1() {
        var url = this.buildUrl("/dictionary");
        var json = "{\"name\":\"blue\",\"status\":\"0\"}";
        var response = httpTemplate.post(url, json);
        Assertions.assertEquals(200, response.getStatusCode());

        var body = response.convertTo(DictionaryResponse.class);
        Assertions.assertEquals("blue", body.getName());
        Assertions.assertEquals(status, body.getStatus());
    }

    @Test
    public void testNumber2() {
        var url = this.buildUrl("/dictionary");
        var json = "{\"name\":\"blue\",\"status\":0}";
        var response = httpTemplate.post(url, json);
        Assertions.assertEquals(200, response.getStatusCode());

        var body = response.convertTo(DictionaryResponse.class);
        Assertions.assertEquals("blue", body.getName());
        Assertions.assertEquals(status, body.getStatus());
    }

}
