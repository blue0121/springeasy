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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class InsertUuidIdSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new InsertIdSqlHandler();

	@BeforeEach
	void beforeEach() {
		parserFacade.parse(UuidStringIdEntity.class);
	}

	@ParameterizedTest
	@CsvSource({"true,","true,abc","false,","false,abc"})
	void testUuidString(boolean isEntity, String id) {
		var config = metadataCache.loadEntityMetadata(UuidStringIdEntity.class);
		SqlRequest request = null;
		if (isEntity) {
			var entity = new UuidStringIdEntity();
			if (id != null && !id.isEmpty()) {
				entity.id = id;
			}
			request = SqlRequest.create(entity, config, true);
		} else {
			Map<String, Object> map = new HashMap<>();
			if (id != null && !id.isEmpty()) {
				map.put("id", id);
			}
			request = SqlRequest.create(map, config);
		}
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertEquals(20, param.get("id").toString().length());
	}

	@NoArgsConstructor
	@Entity
	static class UuidStringIdEntity {
		@Id(generator = GeneratorType.UUID)
		public String id;
		public String name;
	}

}
