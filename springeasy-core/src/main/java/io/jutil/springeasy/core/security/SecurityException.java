package io.jutil.springeasy.core.security;

import java.io.Serial;

/**
 * @author Jin Zheng
 * @since 2023-11-16
 */
public class SecurityException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}
}
