package io.jutil.springeasy.core.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jin Zheng
 * @since 2022-12-21
 */
class AsyncHttpTemplateTest extends BaseHttpTest {

    @BeforeEach
    void beforeEach() {
        this.init();
    }

    @Test
    void testGetString() {
        var url = "/test/blue";
        var response = asyncHttpTemplate.get(url);
        this.verify(response);
    }

    @Test
    void testPostString() {
        var url = "/test";
        var response = asyncHttpTemplate.post(url, "blue");
        this.verify(response);
    }

    @Test
    void testPutString() {
        var url = "/test";
        var response = asyncHttpTemplate.put(url, "blue");
        this.verify(response);
    }

    @Test
    void testPatchString() {
        var url = "/test";
        var response = asyncHttpTemplate.patch(url, "blue");
        this.verify(response);
    }

    @Test
    void testDeleteString() {
        var url = "/test/blue";
        var response = asyncHttpTemplate.delete(url);
        this.verify(response);
    }

    private void verify(CompletableFuture<StringResponse> resp) {
        try {
            var response = resp.get();
            System.out.println(response.getBody());
            Assertions.assertEquals(200, response.getStatusCode());
            Assertions.assertTrue(response.is2xxStatus());
            Assertions.assertEquals("Hello, blue", response.getBody());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void testUpload() throws Exception {
        var path = Paths.get(AsyncHttpTemplateTest.class.getResource("/json/string.json").toURI());
        var url = "/upload";
        var resp = asyncHttpTemplate.upload(url, HttpMethod.POST, path);
        var response = resp.get();
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
        var resp = asyncHttpTemplate.download(url, path);
        var response = resp.get();
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.is2xxStatus());
        Assertions.assertEquals(20L, Files.size(response.getBody()));
    }
}
