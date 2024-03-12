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
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class GetFieldSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new GetFieldSqlHandler();

	@Test
	void testSql1() {
		response.putParam("id", 1);
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create("name", Map.of(), config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select name from usr_group where id=?", response.getSql());
		Assertions.assertEquals(List.of("id"), response.toNameList());
	}

	@Test
	void testSql2() {
		response.putParam("id", 1);
		response.putParam("count", 1);
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create("name", Map.of(), config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select name from usr_group where count=? and id=?", response.getSql());
		Assertions.assertEquals(List.of("count", "id"), response.toNameList());
	}

	@Test
	void testSqlFailure1() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create("name", Map.of(), config);
		Assertions.assertThrows(NullPointerException.class, () -> handler.handle(request, response),
				"参数 不能为空");
	}

	@Test
	void testSqlFailure2() {
		response.putParam("aaa", "aaa");
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create("name", Map.of(), config);
		Assertions.assertThrows(JdoException.class, () -> handler.handle(request, response),
				"字段 [aaa] 不存在");
	}

}
