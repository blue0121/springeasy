package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.HttpMethod;
import io.jutil.springeasy.core.http.HttpTemplate;
import io.jutil.springeasy.core.http.PathResponse;
import io.jutil.springeasy.core.http.StringResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
public class DefaultHttpTemplate extends AbstractHttpTemplate implements HttpTemplate {


	public DefaultHttpTemplate(DefaultHttpTemplateBuilder builder) {
		this.id = builder.getId();
		this.baseUrl = builder.getBaseUrl();
		this.defaultHeaders = Map.copyOf(builder.getDefaultHeaders());
		this.httpClient = builder.getHttpClient();
	}

	@Override
	public StringResponse request(String uri, HttpMethod method, String body,
	                                  Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);

		try {
			var response = httpClient.send(builder.build(),
					HttpResponse.BodyHandlers.ofString());
			return new DefaultStringResponse(response);
		} catch (Exception e) {
			// NOSONAR
			this.handleException(e);
			return null;
		}
	}

	@Override
	public PathResponse download(String uri, HttpMethod method, String body,
	                                 Path file, Map<String, String> header) {
		var publisher = this.publisher(body);
		var builder = this.builder(uri, method, header, publisher);
		try {
			var response = httpClient.send(builder.build(),
					HttpResponse.BodyHandlers.ofFile(file));
			return new DefaultPathResponse(response);
		} catch (Exception e) {
			// NOSONAR
			this.handleException(e);
			return null;
		}
	}

	@Override
	public StringResponse upload(String uri, HttpMethod method, Map<String, String> textParam,
	                                 Map<String, Path> fileParam, Map<String, String> header) {
		var publisher = new MultiPartBodyPublisher();
		header = header == null ? new HashMap<>() : header;
		header.put("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary());
		var pub = this.buildBodyPublisher(publisher, textParam, fileParam);
		var builder = this.builder(uri, method, header, pub);
		try {
			var response = httpClient.send(builder.build(),
					HttpResponse.BodyHandlers.ofString());
			return new DefaultStringResponse(response);
		} catch (Exception e) {
			// NOSONAR
			this.handleException(e);
			return null;
		}
	}

}
