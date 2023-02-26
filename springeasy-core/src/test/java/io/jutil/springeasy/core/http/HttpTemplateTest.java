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
public class HttpTemplateTest extends BaseHttpTest {

    @BeforeEach
    public void beforeEach() {
        this.init();
    }

    @Test
    public void testGetString() {
        var url = "/test/blue";
        var response = httpTemplate.get(url);
        System.out.println(response.getBody());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals("Hello, blue", response.getBody());
    }

    @Test
    public void testUpload() throws Exception {
        var path = Paths.get(HttpTemplateTest.class.getResource("/json/string.json").toURI());
        var url = "/upload";
        var response = httpTemplate.upload(url, HttpMethod.POST, path);
        System.out.println(response.getBody());
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals("name: string.json, size: 20", response.getBody());
    }

    @Test
    public void testDownload() throws Exception {
        var uuid = UUID.randomUUID().toString();
        var url = "/download";
        var path = Files.createTempFile(uuid, ".json");
        var response = httpTemplate.download(url, path);
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals(20L, Files.size(response.getBody()));
    }
}
