package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplateBuilder;
import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2021-07-02
 */
@Slf4j
public class DefaultHttpTemplateBuilder implements HttpTemplateBuilder {

	private String id;
	private String baseUrl;
	private String username;
	private String password;
	private final Map<String, String> defaultHeaders = new HashMap<>();

	private HttpClient httpClient;

	public DefaultHttpTemplateBuilder() {
	}

	@Override
	public HttpTemplate build() {
		this.init();
		return new DefaultHttpTemplate(this);
	}

	private void init() {
		AssertUtil.notNull(httpClient, "HttpClient");

		if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
			defaultHeaders.put("Authorization", this.authorization());
		}
		log.info("Initialize HttpClient, id: {}, baseUrl: {}, username: {}, password: {}, defaultHeaders: {}",
				id, baseUrl, username, password, defaultHeaders);
	}

	private String authorization() {
		String auth = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
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
	public HttpTemplateBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	@Override
	public HttpTemplateBuilder setPassword(String password) {
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Map<String, String> getDefaultHeaders() {
		return defaultHeaders;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
}
