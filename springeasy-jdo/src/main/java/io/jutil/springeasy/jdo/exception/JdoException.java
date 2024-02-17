package io.jutil.springeasy.jdo.exception;

/**
 * @author Jin Zheng
 * @since 2024-02-17
 */
public class JdoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JdoException(String message) {
		super(message);
	}

	public JdoException(Throwable cause) {
		super(cause);
	}
}
