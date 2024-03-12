package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.jdo.model.GroupEntity;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlHandlerTest;
import io.jutil.springeasy.jdo.sql.SqlParameter;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class GetIdSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new GetIdSqlHandler();

	@Test
	void testSql1() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(GroupEntity.class, List.of(1), config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select * from usr_group where id=?", response.getSql());
		Assertions.assertEquals(List.of(SqlParameter.create(1)), response.toParameterList());
	}

	@Test
	void testSql2() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(GroupEntity.class, List.of(1, 2), config);
		handler.handle(request, response);
		System.out.println(response.getSql());
		Assertions.assertEquals("select * from usr_group where id in (?,?)", response.getSql());
		Assertions.assertEquals(List.of(SqlParameter.create(1), SqlParameter.create(2)), response.toParameterList());
	}

	@Test
	void testSqlFailure1() {
		var config = metadataCache.loadEntityMetadata(GroupEntity.class);
		var request = SqlRequest.create(null, config);
		Assertions.assertThrows(NullPointerException.class, () -> handler.handle(request, response),
				"参数 不能为空");
	}

}
