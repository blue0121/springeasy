package io.jutil.springeasy.jdo.exception;

import java.io.Serial;

/**
 * @author Jin Zheng
 * @since 2024-02-17
 */
public class JdoException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public JdoException(String message) {
		super(message);
	}

	public JdoException(Throwable cause) {
		super(cause);
	}
}
