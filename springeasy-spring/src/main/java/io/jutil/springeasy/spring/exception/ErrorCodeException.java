package io.jutil.springeasy.spring.exception;

/**
 * @author Jin Zheng
 * @since 2023-10-05
 */
@SuppressWarnings("javaarchitecture:S7027")
public class ErrorCodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final transient ErrorCode errorCode;
	private final transient Object[] args;
	private final String message;

	public ErrorCodeException(ErrorCode errorCode, Object... args) {
		this.errorCode = errorCode;
		this.args = args;
		this.message = errorCode.getMessage(args);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public int getCode() {
		return errorCode.code();
	}

	public int getHttpStatus() {
		return errorCode.httpStatus();
	}

	public String toJsonString() {
		return errorCode.toJsonString(args);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ": " + message;
	}
}
