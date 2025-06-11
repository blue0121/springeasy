package io.jutil.springeasy.mybatis.exception;

import java.io.Serial;
import java.sql.SQLException;

/**
 * @author Jin Zheng
 * @since 2025-06-05
 */
public class JdbcException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public JdbcException(SQLException e) {
		super(e);
	}

	public JdbcException(String message, SQLException e) {
		super(message, e);
	}
}
