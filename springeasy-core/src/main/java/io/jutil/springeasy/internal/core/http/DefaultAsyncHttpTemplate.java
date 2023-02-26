package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.AsyncHttpTemplate;
import io.jutil.springeasy.core.http.HttpMethod;
import io.jutil.springeasy.core.http.PathResponse;
import io.jutil.springeasy.core.http.StringResponse;
import lombok.extern.slf4j.Slf4j;

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
public class DefaultAsyncHttpTemplate extends AbstractHttpTemplate implements AsyncHttpTemplate {

	public DefaultAsyncHttpTemplate(DefaultHttpTemplateBuilder builder) {
		this.id = builder.getId();
		this.baseUrl = builder.getBaseUrl();
		this.defaultHeaders = Map.copyOf(builder.getDefaultHeaders());
		this.httpClient = builder.getHttpClient();
	}

	@Override
	public CompletableFuture<StringResponse> request(String uri, HttpMethod method,
	                                                 String body, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		var future = httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString());
		return future.thenApply(s -> new DefaultStringResponse(s));
	}

	@Override
	public CompletableFuture<PathResponse> download(String uri, HttpMethod method, String body,
	                                                Path file, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		var future = httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofFile(file));
		return future.thenApply(s -> new DefaultPathResponse(s));
	}

	@Override
	public CompletableFuture<StringResponse> upload(String uri, HttpMethod method,
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

}
