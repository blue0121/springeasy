package io.jutil.springeasy.mybatis.metadata;

import io.jutil.springeasy.mybatis.BaseTest;
import io.jutil.springeasy.test.container.MySQLTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author Jin Zheng
 * @since 2025-06-11
 */
class MySQLMetadataIT implements MySQLTest, BaseTest {
	@Autowired
	DataSource dataSource;

	@Test
	void testGetDatabaseInfo() {
		var info = MetadataUtil.getDatabaseInfo(dataSource);
		Assertions.assertEquals(DatabaseProduct.MYSQL, info.product());
	}
}
