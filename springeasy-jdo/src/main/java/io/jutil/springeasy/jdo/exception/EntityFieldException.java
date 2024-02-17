package io.jutil.springeasy.jdo.exception;

/**
 * @author Jin Zheng
 * @since 2024-02-17
 */
public class EntityFieldException extends JdoException {
	private static final long serialVersionUID = 1L;

	public EntityFieldException(String field) {
		super("字段 [" + field + "] 不存在");
	}

	public EntityFieldException(String field, String message) {
		super("字段 [" + field + "] " + message);
	}

}
