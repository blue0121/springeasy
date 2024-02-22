package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Jin Zheng
 * @since 2024-02-19
 */
@Slf4j
public class JdbcUtil {
	private JdbcUtil() {
	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		close(rs);
		close(stmt);
		close(conn);
	}

	public static void close(AutoCloseable res) {
		if (res != null) {
			try {
				res.close();
			} catch (Exception e) {
				log.warn("资源关闭错误, ", e);
			}
		}
	}
}
