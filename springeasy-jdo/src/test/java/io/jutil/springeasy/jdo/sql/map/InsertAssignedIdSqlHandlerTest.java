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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class InsertAssignedIdSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new InsertIdSqlHandler();

	@BeforeEach
	void beforeEach() {
		parserFacade.parse(AssignedLongIdEntity.class);
		parserFacade.parse(AssignedStringIdEntity.class);
	}

	@ParameterizedTest
	@CsvSource({"true,-1","true,10","false,-1","false,10"})
	void testAssignedLong(boolean isEntity, long id) {
		var config = metadataCache.loadEntityMetadata(AssignedLongIdEntity.class);
		SqlRequest request = null;
		if (isEntity) {
			var entity = new AssignedLongIdEntity();
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
			Assertions.assertEquals(id, param.get("id"));
		} else {
			SqlRequest req = request;
			Assertions.assertThrows(EntityFieldException.class, () -> handler.handle(req, response));
		}
	}

	@ParameterizedTest
	@CsvSource({"true,","true,abc","false,","false,abc"})
	void testAssignedString(boolean isEntity, String id) {
		var config = metadataCache.loadEntityMetadata(AssignedStringIdEntity.class);
		SqlRequest request = null;
		if (isEntity) {
			var entity = new AssignedStringIdEntity();
			entity.id = id;
			request = SqlRequest.create(entity, config, true);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			request = SqlRequest.create(map, config);
		}

		if (id != null && !id.isEmpty()) {
			handler.handle(request, response);
			var param = response.toParamMap();
			Assertions.assertEquals(1, param.size());
			Assertions.assertEquals(id, param.get("id"));
		} else {
			SqlRequest req = request;
			Assertions.assertThrows(EntityFieldException.class, () -> handler.handle(req, response));
		}
	}

	@NoArgsConstructor
	@Entity
	static class AssignedLongIdEntity {
		@Id(generator = GeneratorType.ASSIGNED)
		public Long id;
		public String name;
	}

	@NoArgsConstructor
	@Entity
	static class AssignedStringIdEntity {
		@Id(generator = GeneratorType.ASSIGNED)
		public String id;
		public String name;
	}

}
