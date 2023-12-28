package io.jutil.springeasy.core.http;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;
import java.nio.file.Path;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
class DefaultPathResponse extends AbstractResponse<Path> implements PathResponse {
	public DefaultPathResponse(HttpResponse<Path> response) {
		super(response);

		if (log.isDebugEnabled()) {
			log.info("Http Response, code: {}, headers: {}, path: {}",
					this.getStatusCode(), this.getHeaders(), this.getBody());
		} else {
			log.info("Http Response, code: {}, path: {}", this.getStatusCode(), this.getBody());
		}
	}
}
