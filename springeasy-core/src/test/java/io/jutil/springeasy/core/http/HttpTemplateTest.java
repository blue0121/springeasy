package io.jutil.springeasy.core.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2022-12-21
 */
class HttpTemplateTest extends BaseHttpTest {

    @BeforeEach
    void beforeEach() {
        this.init();
    }

    @Test
    void testGetString() {
        var url = "/test/blue";
        var response = httpTemplate.get(url);
        this.verify(response);
    }

    @Test
    void testPostString() {
        var url = "/test";
        var response = httpTemplate.post(url, "blue");
        this.verify(response);
    }

    @Test
    void testPutString() {
        var url = "/test";
        var response = httpTemplate.put(url, "blue");
        this.verify(response);
    }

    @Test
    void testPatchString() {
        var url = "/test";
        var response = httpTemplate.patch(url, "blue");
        this.verify(response);
    }

    @Test
    void testDeleteString() {
        var url = "/test/blue";
        var response = httpTemplate.delete(url);
        this.verify(response);
    }

    private void verify(StringResponse response) {
        System.out.println(response.getBody());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals("Hello, blue", response.getBody());
    }

    @Test
    void testUpload() throws Exception {
        var path = Paths.get(HttpTemplateTest.class.getResource("/json/string.json").toURI());
        var url = "/upload";
        var response = httpTemplate.upload(url, HttpMethod.POST, path);
        System.out.println(response.getBody());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals("name: string.json, size: 20", response.getBody());
    }

    @Test
    void testDownload() throws Exception {
        var uuid = UUID.randomUUID().toString();
        var url = "/download";
        var path = Files.createTempFile(uuid, ".json");
        var response = httpTemplate.download(url, path);
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals(20L, Files.size(response.getBody()));
    }
}
