package io.jutil.springeasy.core.io;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class PackException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PackException(String message) {
		super(message);
	}

	public PackException(Throwable cause) {
		super(cause);
	}

}
