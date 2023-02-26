package io.jutil.springeasy.spring.bean;

import io.jutil.springeasy.core.http.AsyncHttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplateBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-20
 */
public class AsyncHttpTemplateFactoryBean implements FactoryBean<AsyncHttpTemplate>, InitializingBean {
	private String id;
	private String baseUrl;
	private Map<String, String> defaultHeaders;
	private HttpClient httpClient;

	private AsyncHttpTemplate httpTemplate;

	public AsyncHttpTemplateFactoryBean() {
	}

	@Override
	public AsyncHttpTemplate getObject() throws Exception {
		return httpTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return AsyncHttpTemplate.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.httpTemplate = HttpTemplateBuilder.create()
				.setId(id)
				.setBaseUrl(baseUrl)
				.putDefaultHeaders(defaultHeaders)
				.setHttpClient(httpClient)
				.buildAsync();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setDefaultHeaders(Map<String, String> defaultHeaders) {
		this.defaultHeaders = defaultHeaders;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
