package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-02-26
 */
@Slf4j
public abstract class AbstractHttpTemplate {

	protected String id;
	protected String baseUrl;
	protected Map<String, String> defaultHeaders;
	protected HttpClient httpClient;

	protected HttpRequest.BodyPublisher publisher(String body) {
		if (body != null && !body.isEmpty()) {
			return HttpRequest.BodyPublishers.ofString(body);
		}

		return HttpRequest.BodyPublishers.noBody();
	}

	protected HttpRequest.Builder builder(String uri, HttpMethod method,
	                                    Map<String, String> header,
	                                    HttpRequest.BodyPublisher publisher) {
		String url = (baseUrl != null && !baseUrl.isEmpty()) ? baseUrl + uri : uri;
		var builder = HttpRequest.newBuilder(URI.create(url));
		Map<String, String> map = new HashMap<>();
		if (defaultHeaders != null && !defaultHeaders.isEmpty()) {
			map.putAll(defaultHeaders);
		}
		if (header != null && !header.isEmpty()) {
			map.putAll(header);
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}
		builder.method(method.name(), publisher);
		log.info("{} url: {}, headers: {}", method, url, map);
		return builder;
	}

	protected HttpRequest.BodyPublisher buildBodyPublisher(MultiPartBodyPublisher publisher,
	                                                       Map<String, String> textParam,
	                                                       Map<String, Path> fileParam) {
		if (textParam != null && !textParam.isEmpty()) {
			for (var entry : textParam.entrySet()) {
				publisher.addPart(entry.getKey(), entry.getValue());
			}
		}
		if (fileParam != null && !fileParam.isEmpty()) {
			for (var entry : fileParam.entrySet()) {
				publisher.addPart(entry.getKey(), entry.getValue());
			}
		}
		return publisher.build();
	}

	protected void handleException(Exception cause) {
		log.error("Error, ", cause);
		if (cause instanceof IOException ex) {
			throw new UncheckedIOException(ex);
		}
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
}
