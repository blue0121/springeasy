package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.model.GroupEntity;
import io.jutil.springeasy.jdo.model.UserEntity;
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
class IncSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new IncSqlHandler();

	@Test
	void testSql1() {
		response.putParam("count", 1);
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("update usr_group set count=count+? where id=?", response.getSql());
		Assertions.assertEquals(List.of("count", "id"), response.toNameList());
	}

	@Test
	void testSql2() {
		response.putParam("groupId", 1);
		var config = metadataCache.loadEntityMetadata(UserEntity.class);
		var request = SqlRequest.create(null, config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("update usr_user set group_id=group_id+? where id=?", response.getSql());
		Assertions.assertEquals(List.of("groupId", "id"), response.toNameList());
	}

	@Test
	void testSqlFailure1() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(NullPointerException.class, () -> handler.handle(request, response),
				"参数 不能为空");
	}

	@Test
	void testSqlFailure3() {
		response.putParam("count", "aaa");
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(JdoException.class, () -> handler.handle(request, response),
				"字段 [aaa] 不是数字");
	}

	@Test
	void testSqlFailure4() {
		response.putParam("name", 1);
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(JdoException.class, () -> handler.handle(request, response),
				"字段 [name] 不是数字");
	}

}
