package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.HttpMethod;
import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.http.PathResponse;
import io.jutil.springeasy.core.http.StringResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
public class DefaultHttpTemplate implements HttpTemplate {

	private final DefaultHttpTemplateBuilder builder;
	private final String id;
	private final String baseUrl;
	private final Map<String, String> defaultHeaders;
	private final HttpClient httpClient;

	public DefaultHttpTemplate(DefaultHttpTemplateBuilder builder) {
		this.builder = builder;
		this.id = builder.getId();
		this.baseUrl = builder.getBaseUrl();
		this.defaultHeaders = Map.copyOf(builder.getDefaultHeaders());
		this.httpClient = builder.getHttpClient();
	}

	@Override
	public StringResponse requestSync(String uri, HttpMethod method, String body,
	                                  Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);

		try {
			var response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
			return new DefaultStringResponse(response);
		}
		catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	@Override
	public CompletableFuture<StringResponse> requestAsync(String uri, HttpMethod method,
	                                                      String body, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		var future = httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString());
		return future.thenApply(s -> new DefaultStringResponse(s));
	}

	@Override
	public PathResponse downloadSync(String uri, HttpMethod method, String body,
	                                 Path file, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		try {
			var response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofFile(file));
			return new DefaultPathResponse(response);
		}
		catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	@Override
	public CompletableFuture<PathResponse> downloadAsync(String uri, HttpMethod method, String body,
	                                                     Path file, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		var future = httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofFile(file));
		return future.thenApply(s -> new DefaultPathResponse(s));
	}

	@Override
	public StringResponse uploadSync(String uri, HttpMethod method, Map<String, String> textParam,
	                                 Map<String, Path> fileParam, Map<String, String> header) {
		var publisher = new MultiPartBodyPublisher();
		header = header == null ? new HashMap<>() : header;
		header.put("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
		var pub = this.buildBodyPublisher(publisher, textParam, fileParam);
		var builder = this.builder(uri, method, header, pub);
		try {
			var response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
			return new DefaultStringResponse(response);
		}
		catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	@Override
	public CompletableFuture<StringResponse> uploadAsync(String uri, HttpMethod method,
	                                                     Map<String, String> textParam,
	                                                     Map<String, Path> fileParam,
	                                                     Map<String, String> header) {
		var publisher = new MultiPartBodyPublisher();
		header = header == null ? new HashMap<>() : header;
		header.put("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
		var pub = this.buildBodyPublisher(publisher, textParam, fileParam);
		var builder = this.builder(uri, method, header, pub);
		var future = httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString());
		return future.thenApply(s -> new DefaultStringResponse(s));
	}

	private HttpRequest.Builder builder(String uri, HttpMethod method,
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

	private HttpRequest.BodyPublisher publisher(String body) {
		if (body != null && !body.isEmpty()) {
			return HttpRequest.BodyPublishers.ofString(body);
		}

		return HttpRequest.BodyPublishers.noBody();
	}

	private void handleException(Exception cause) {
		log.error("Error, ", cause);
		if (cause instanceof IOException) {
			throw new UncheckedIOException((IOException) cause);
		}
	}

	private HttpRequest.BodyPublisher buildBodyPublisher(MultiPartBodyPublisher publisher,
	                                                     Map<String, String> textParam,
	                                                     Map<String, Path> fileParam) {
		if (textParam != null && !textParam.isEmpty()) {
			for (Map.Entry<String, String> entry : textParam.entrySet()) {
				publisher.addPart(entry.getKey(), entry.getValue());
			}
		}
		if (fileParam != null && !fileParam.isEmpty()) {
			for (Map.Entry<String, Path> entry : fileParam.entrySet()) {
				publisher.addPart(entry.getKey(), entry.getValue());
			}
		}
		return publisher.build();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getBaseUrl() {
		return baseUrl;
	}

	@Override
	public String getUsername() {
		return builder.getUsername();
	}

	@Override
	public String getPassword() {
		return builder.getPassword();
	}

	@Override
	public Map<String, String> getDefaultHeaders() {
		return defaultHeaders;
	}

}
