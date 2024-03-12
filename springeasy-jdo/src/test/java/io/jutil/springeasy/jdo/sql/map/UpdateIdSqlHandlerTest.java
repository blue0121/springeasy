package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
import io.jutil.springeasy.jdo.exception.EntityFieldException;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlHandlerTest;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class UpdateIdSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new UpdateIdSqlHandler();

	@BeforeEach
	void beforeEach() {
		parserFacade.parse(IdEntity.class);
	}

	@ParameterizedTest
	@CsvSource({"true,-1","true,10","false,-1","false,10"})
	void testHandle(boolean isEntity, Long id) {
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		SqlRequest request = null;
		if (isEntity) {
			var entity = new IdEntity();
			if (id > 0) {
				entity.id = id;
			}
			request = SqlRequest.create(entity, config, true);
		} else {
			Map<String, Object> map = new HashMap<>();
			if (id > 0) {
				map.put("id", id);
			}
			request = SqlRequest.create(map, config);
		}

		if (id > 0) {
			handler.handle(request, response);
			var param = response.toParamMap();
			Assertions.assertEquals(1, param.size());
			Assertions.assertEquals(10L, param.get("id"));
		} else {
			SqlRequest req = request;
			Assertions.assertThrows(EntityFieldException.class, () -> handler.handle(req, response));
		}
	}

	@Test
	void testHandleMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		var config = metadataCache.loadEntityMetadata(IdEntity.class);
		var request = SqlRequest.create(map, config);
		Assertions.assertThrows(EntityFieldException.class, () -> handler.handle(request, response));
	}

	@NoArgsConstructor
	@Entity
	static class IdEntity {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		public String name;
	}

}
