package io.jutil.springeasy.core.http;

import io.jutil.springeasy.core.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.http.HttpClient;

/**
 * @author Jin Zheng
 * @since 2022-12-21
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseHttpTest {
    @LocalServerPort
    protected int port;

    protected HttpTemplate httpTemplate;
    protected AsyncHttpTemplate asyncHttpTemplate;


    protected void init() {
        var httpClient = HttpClient.newHttpClient();
        var baseUrl = "http://localhost:" + port;
        httpTemplate = HttpTemplateBuilder.create()
                .setBaseUrl(baseUrl)
                .setHttpClient(httpClient)
                .build();

        asyncHttpTemplate = HttpTemplateBuilder.create()
                .setBaseUrl(baseUrl)
                .setHttpClient(httpClient)
                .buildAsync();

    }
}
