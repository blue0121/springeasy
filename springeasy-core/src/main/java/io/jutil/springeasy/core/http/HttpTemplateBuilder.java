package io.jutil.springeasy.core.http;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
public interface HttpTemplateBuilder {

	static HttpTemplateBuilder create() {
		return new DefaultHttpTemplateBuilder();
	}

	HttpTemplate build();

	AsyncHttpTemplate buildAsync();

	HttpTemplateBuilder setId(String id);

	HttpTemplateBuilder setBaseUrl(String baseUrl);

	HttpTemplateBuilder putDefaultHeader(String name, String value);

	HttpTemplateBuilder putDefaultHeaders(Map<String, String> headers);

	HttpTemplateBuilder setHttpClient(HttpClient httpClient);

}
