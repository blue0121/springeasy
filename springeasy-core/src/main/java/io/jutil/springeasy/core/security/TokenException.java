package io.jutil.springeasy.core.security;

import java.io.Serial;

/**
 * @author Jin Zheng
 * @since 2023-12-04
 */
public class TokenException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Throwable cause) {
		super(cause);
	}
}