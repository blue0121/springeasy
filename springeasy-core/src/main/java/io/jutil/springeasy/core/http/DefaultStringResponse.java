package io.jutil.springeasy.core.http;

import io.jutil.springeasy.core.codec.json.Json;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
class DefaultStringResponse extends AbstractResponse<String> implements StringResponse {
	DefaultStringResponse(HttpResponse<String> response) {
		super(response);

		if (log.isDebugEnabled()) {
			log.debug("Http Response, code: {}, headers: {}, body: {}",
					this.getStatusCode(), this.getHeaders().map(), this.getBody());
		} else {
			log.info("Http Response, code: {}", this.getStatusCode());
		}
	}

	@Override
	public <T> T convertTo(Class<T> clazz) {
		return Json.fromString(this.getBody(), clazz);
	}
}
