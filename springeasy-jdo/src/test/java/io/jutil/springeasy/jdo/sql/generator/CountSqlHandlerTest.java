package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.model.GroupEntity;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlHandlerTest;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class CountSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new CountSqlHandler();


	@Test
	void testSql1() {
		response.putParam("name", "blue");
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select count(*) from usr_group where name=?", response.getSql());
		Assertions.assertEquals(List.of("name"), response.toNameList());
	}

	@Test
	void testSql2() {
		response.putParam("name", "blue");
		response.putParam("count", 1);
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select count(*) from usr_group where name=? and count=?", response.getSql());
		Assertions.assertEquals(List.of("name", "count"), response.toNameList());
	}

	@Test
	void testSqlFailure1() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(NullPointerException.class, () -> handler.handle(request, response),
				"参数 不能为空");
	}

	@Test
	void testSqlFailure2() {
		response.putParam("aaa", "aaa");
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(JdoException.class, () -> handler.handle(request, response),
				"字段 [aaa] 不存在");
	}

}
