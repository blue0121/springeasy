package io.jutil.springeasy.mybatis.metadata;

/**
 * @author Jin Zheng
 * @since 2025-06-05
 */
public record DatabaseInfo(DatabaseProduct product, String databaseVersion,
                           String driverName, String driverVersion) {
}
