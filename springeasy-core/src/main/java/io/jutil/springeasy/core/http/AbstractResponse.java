package io.jutil.springeasy.core.http;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
abstract class AbstractResponse<T> implements Response<T> {

	private int statusCode;
	private HttpHeaders headers;
	private T body;

	AbstractResponse(HttpResponse<T> response) {
		this.statusCode = response.statusCode();
		this.body = response.body();
		this.headers = response.headers();
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public boolean is2xxStatus() {
		return statusCode >= 200 && statusCode < 300;
	}

	@Override
	public boolean is4xxStatus() {
		return statusCode >= 400 && statusCode < 500;
	}

	@Override
	public boolean is5xxStatus() {
		return statusCode >= 500;
	}

	@Override
	public HttpHeaders getHeaders() {
		return headers;
	}

	@Override
	public T getBody() {
		return body;
	}
}
