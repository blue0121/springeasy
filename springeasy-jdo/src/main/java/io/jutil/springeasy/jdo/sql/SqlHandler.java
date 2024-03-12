package io.jutil.springeasy.jdo.sql;

/**
 * @author Jin Zheng
 * @since 2024-03-08
 */
public interface SqlHandler {

	void handle(SqlRequest request, SqlResponse response);

}
