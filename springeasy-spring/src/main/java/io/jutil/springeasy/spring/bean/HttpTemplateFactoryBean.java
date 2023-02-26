package io.jutil.springeasy.spring.bean;

import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.http.HttpTemplateBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 1.0 2021-04-20
 */
public class HttpTemplateFactoryBean implements FactoryBean<HttpTemplate>, InitializingBean {
	private String id;
	private String baseUrl;
	private Map<String, String> defaultHeaders;
	private HttpClient httpClient;

	private HttpTemplate httpTemplate;

	public HttpTemplateFactoryBean() {
	}

	@Override
	public HttpTemplate getObject() throws Exception {
		return httpTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return HttpTemplate.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.httpTemplate = HttpTemplateBuilder.create()
				.setId(id)
				.setBaseUrl(baseUrl)
				.putDefaultHeaders(defaultHeaders)
				.setHttpClient(httpClient)
				.build();
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
