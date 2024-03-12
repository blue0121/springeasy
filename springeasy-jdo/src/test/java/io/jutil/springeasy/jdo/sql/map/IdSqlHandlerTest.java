package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlHandlerTest;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class IdSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new IdSqlHandler();

	@BeforeEach
	void beforeEach() {
		parserFacade.parse(IdEntity.class);
	}

	@Test
	void testHandleObject1() {
		var column = new IdEntity();
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		var request = SqlRequest.create(column, config, false);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertTrue(param.containsKey("id"));
		Assertions.assertNull(param.get("id"));
	}

	@Test
	void testHandleObject2() {
		var column = new IdEntity();
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		var request = SqlRequest.create(column, config, true);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertTrue(param.isEmpty());
	}

	@Test
	void testHandleMap1() {
		Map<String, Object> map = new HashMap<>();
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		var request = SqlRequest.create(map, config);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertTrue(param.isEmpty());
	}

	@Test
	void testHandleMap2() {
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		var request = SqlRequest.create(map, config);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertTrue(param.isEmpty());
	}

	@NoArgsConstructor
	@Entity
	static class IdEntity {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		public String name;
	}

}
