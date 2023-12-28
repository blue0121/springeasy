package io.jutil.springeasy.core.http;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
public interface AsyncHttpTemplate {

	default CompletableFuture<StringResponse> get(String uri) {
		return this.request(uri, HttpMethod.GET, null, null);
	}

	default CompletableFuture<StringResponse> get(String uri, Map<String, String> headers) {
		return this.request(uri, HttpMethod.GET, null, headers);
	}

	default CompletableFuture<StringResponse> post(String uri, String body) {
		return this.request(uri, HttpMethod.POST, body, null);
	}

	default CompletableFuture<StringResponse> post(String uri, String body,
	                                               Map<String, String> headers) {
		return this.request(uri, HttpMethod.POST, body, headers);
	}

	default CompletableFuture<StringResponse> patch(String uri, String body) {
		return this.request(uri, HttpMethod.PATCH, body, null);
	}

	default CompletableFuture<StringResponse> patch(String uri, String body,
	                                                Map<String, String> headers) {
		return this.request(uri, HttpMethod.PATCH, body, headers);
	}

	default CompletableFuture<StringResponse> put(String uri, String body) {
		return this.request(uri, HttpMethod.PUT, body, null);
	}

	default CompletableFuture<StringResponse> put(String uri, String body,
	                                              Map<String, String> headers) {
		return this.request(uri, HttpMethod.PUT, body, headers);
	}

	default CompletableFuture<StringResponse> delete(String uri) {
		return this.request(uri, HttpMethod.DELETE, null, null);
	}

	default CompletableFuture<StringResponse> delete(String uri, Map<String, String> headers) {
		return this.request(uri, HttpMethod.DELETE, null, headers);
	}

	default CompletableFuture<StringResponse> request(String uri, HttpMethod method,
	                                                  String body) {
		return this.request(uri, method, body, null);
	}

	CompletableFuture<StringResponse> request(String uri, HttpMethod method,
	                                          String body, Map<String, String> header);

	default CompletableFuture<PathResponse> download(String uri, Path file) {
		return this.download(uri, HttpMethod.GET, null, file, null);
	}

	default CompletableFuture<PathResponse> download(String uri, HttpMethod method,
	                                                 String body, Path file) {
		return this.download(uri, method, body, file, null);
	}

	CompletableFuture<PathResponse> download(String uri, HttpMethod method, String body,
	                                         Path file, Map<String, String> header);


	default CompletableFuture<StringResponse> upload(String uri, Path file) {
		Map<String, Path> fileParam = Map.of(HttpConst.KEY_FILE_UPLOAD, file);
		return this.upload(uri, HttpMethod.POST, null, fileParam, null);
	}

	default CompletableFuture<StringResponse> upload(String uri, HttpMethod method,
	                                                 Path file) {
		Map<String, Path> fileParam = Map.of(HttpConst.KEY_FILE_UPLOAD, file);
		return this.upload(uri, method, null, fileParam, null);
	}

	default CompletableFuture<StringResponse> upload(String uri, HttpMethod method,
	                                                 Path file, Map<String, String> header) {
		Map<String, Path> fileParam = Map.of(HttpConst.KEY_FILE_UPLOAD, file);
		return this.upload(uri, method, null, fileParam, header);
	}

	CompletableFuture<StringResponse> upload(String uri, HttpMethod method,
	                                         Map<String, String> textParam,
	                                         Map<String, Path> fileParam,
	                                         Map<String, String> header);

	String getId();

	String getBaseUrl();

	Map<String, String> getDefaultHeaders();

}
