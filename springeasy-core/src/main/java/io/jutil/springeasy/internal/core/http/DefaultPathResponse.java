package io.jutil.springeasy.internal.core.http;

import io.jutil.springeasy.core.http.PathResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;
import java.nio.file.Path;

/**
 * @author Jin Zheng
 * @date 2020-07-08
 */
@Slf4j
public class DefaultPathResponse extends AbstractResponse<Path> implements PathResponse {
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
