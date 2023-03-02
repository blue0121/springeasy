package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.AsyncHttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplateBuilder;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
@Slf4j
@NoArgsConstructor
public class DefaultHttpTemplateBuilder implements HttpTemplateBuilder {

	private String id;
	private String baseUrl;
	private final Map<String, String> defaultHeaders = new HashMap<>();

	private HttpClient httpClient;


	@Override
	public HttpTemplate build() {
		this.init();
		return new DefaultHttpTemplate(this);
	}

	@Override
	public AsyncHttpTemplate buildAsync() {
		this.init();
		return new DefaultAsyncHttpTemplate(this);
	}

	private void init() {
		AssertUtil.notNull(httpClient, "HttpClient");

		log.info("Initialize HttpClient, id: {}, baseUrl: {}, defaultHeaders: {}",
				id, baseUrl, defaultHeaders);
	}

	@Override
	public HttpTemplateBuilder setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public HttpTemplateBuilder setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	@Override
	public HttpTemplateBuilder putDefaultHeader(String name, String value) {
		this.defaultHeaders.put(name, value);
		return this;
	}

	@Override
	public HttpTemplateBuilder putDefaultHeaders(Map<String, String> headers) {
		if (headers != null && !headers.isEmpty()) {
			this.defaultHeaders.putAll(headers);
		}
		return this;
	}

	@Override
	public HttpTemplateBuilder setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public Map<String, String> getDefaultHeaders() {
		return defaultHeaders;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
}
