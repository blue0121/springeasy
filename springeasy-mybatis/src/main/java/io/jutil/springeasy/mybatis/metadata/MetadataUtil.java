package io.jutil.springeasy.mybatis.metadata;

import io.jutil.springeasy.mybatis.exception.JdbcException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jin Zheng
 * @since 2025-06-05
 */
@Slf4j
public class MetadataUtil {
	private MetadataUtil() {
	}

	public static DatabaseInfo getDatabaseInfo(DataSource dataSource) {
		try (Connection conn = dataSource.getConnection()) {
			var metadata = conn.getMetaData();

			var databaseName = metadata.getDatabaseProductName();
			var databaseVersion = metadata.getDatabaseProductVersion();
			var driverName = metadata.getDriverName();
			var driverVersion = metadata.getDriverVersion();

			log.info("数据库名称: {}, 数据库版本: {}, 驱动程序名称: {}, 驱动程序版本: {}",
					databaseName, databaseVersion, driverName, driverVersion);

			var product = DatabaseProduct.getProduct(databaseName);
			return new DatabaseInfo(product, databaseVersion,
					driverName, driverVersion);
		} catch (SQLException e) {
			throw new JdbcException("无法获取数据库链接", e);
		}
	}
}
