package io.jutil.springeasy.test.container;

import io.jutil.springeasy.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jin Zheng
 * @since 2025-07-12
 */
class MySQLTestContainerIT extends BaseMySQLTest implements BaseTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void beforeEach() {
		jdbcTemplate.update("TRUNCATE TABLE uc_user");
	}

	@Test
	void test() {
		var id = 1;
		var name = "blue";
		var insertSql = "INSERT INTO uc_user (id, name) VALUES (?, ?)";
		jdbcTemplate.update(insertSql, id, name);

		var selectSql = "SELECT name FROM uc_user WHERE id = ?";
		var view = jdbcTemplate.queryForObject(selectSql, String.class, id);
		Assertions.assertEquals(name, view);
	}
}
