package io.jutil.springeasy.core.http;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
public interface HttpTemplateBuilder {

	HttpTemplate build();

	HttpTemplateBuilder setId(String id);

	HttpTemplateBuilder setBaseUrl(String baseUrl);

	HttpTemplateBuilder setUsername(String username);

	HttpTemplateBuilder setPassword(String password);

	HttpTemplateBuilder putDefaultHeader(String name, String value);

	HttpTemplateBuilder putDefaultHeaders(Map<String, String> headers);

	HttpTemplateBuilder setHttpClient(HttpClient httpClient);

}
