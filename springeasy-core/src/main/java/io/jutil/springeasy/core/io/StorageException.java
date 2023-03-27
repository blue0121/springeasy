package io.jutil.springeasy.core.io;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
public class StorageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

}
