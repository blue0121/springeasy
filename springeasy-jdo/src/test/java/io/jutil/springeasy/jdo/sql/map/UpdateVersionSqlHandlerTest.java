package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.annotation.Entity;
import io.jutil.springeasy.jdo.annotation.GeneratorType;
import io.jutil.springeasy.jdo.annotation.Id;
import io.jutil.springeasy.jdo.annotation.Version;
import io.jutil.springeasy.jdo.exception.VersionException;
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
class UpdateVersionSqlHandlerTest extends SqlHandlerTest {
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new UpdateVersionSqlHandler();

	@BeforeEach
	void beforeEach() {
		parserFacade.parse(ForceVersionEntity.class);
		parserFacade.parse(NonForceVersionEntity.class);
	}

	@Test
	void testHandleForceVersionEntity1() {
		var version = new ForceVersionEntity();
		version.version = 10;
		var config = metadataCache.loadEntityMetadata(ForceVersionEntity.class);
		var request = SqlRequest.create(version, config, true);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertEquals(10, param.get("version"));
	}

	@Test
	void testHandleForceVersionEntity2() {
		var version = new ForceVersionEntity();
		var config = metadataCache.loadEntityMetadata(ForceVersionEntity.class);
		var request = SqlRequest.create(version, config, true);
		Assertions.assertThrows(VersionException.class, () -> handler.handle(request, response));
	}

	@Test
	void testHandleNonForceVersionEntity1() {
		var version = new NonForceVersionEntity();
		var config = metadataCache.loadEntityMetadata(NonForceVersionEntity.class);
		var request = SqlRequest.create(version, config, true);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertTrue(param.isEmpty());
	}

	@Test
	void testHandleNonForceVersionEntity2() {
		var version = new NonForceVersionEntity();
		version.version = 10;
		var config = metadataCache.loadEntityMetadata(NonForceVersionEntity.class);
		var request = SqlRequest.create(version, config, true);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertEquals(10, param.get("version"));
	}

	@Test
	void testHandleForceVersionMap1() {
		Map<String, Object> map = new HashMap<>();
		map.put("version", 10);
		var config = metadataCache.loadEntityMetadata(ForceVersionEntity.class);
		var request = SqlRequest.create(map, config);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertEquals(10, param.get("version"));
	}

	@Test
	void testHandleForceVersionMap2() {
		Map<String, Object> map = new HashMap<>();
		var config = metadataCache.loadEntityMetadata(ForceVersionEntity.class);
		var request = SqlRequest.create(map, config);
		Assertions.assertThrows(VersionException.class, () -> handler.handle(request, response));
	}

	@Test
	void testHandleNonForceVersionMap1() {
		Map<String, Object> map = new HashMap<>();
		map.put("version", 10);
		var config = metadataCache.loadEntityMetadata(NonForceVersionEntity.class);
		var request = SqlRequest.create(map, config);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertEquals(1, param.size());
		Assertions.assertEquals(10, param.get("version"));
	}

	@Test
	void testHandleNonForceVersionMap2() {
		Map<String, Object> map = new HashMap<>();
		var config = metadataCache.loadEntityMetadata(NonForceVersionEntity.class);
		var request = SqlRequest.create(map, config);
		handler.handle(request, response);
		var param = response.toParamMap();
		Assertions.assertTrue(param.isEmpty());
	}

	@NoArgsConstructor
	@Entity
	static class ForceVersionEntity {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		@Version
		public Integer version;
		public String name;
	}

	@NoArgsConstructor
	@Entity
	static class NonForceVersionEntity {
		@Id(generator = GeneratorType.SNOWFLAKE)
		public Long id;
		@Version(force = false)
		public Integer version;
		public String name;
	}

}
