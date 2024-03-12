package io.jutil.springeasy.jdo.sql.parameter;

import io.jutil.springeasy.jdo.parser.EntityMetadata;
import io.jutil.springeasy.jdo.sql.SqlHandler;
import io.jutil.springeasy.jdo.sql.SqlParameter;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class ParameterSqlHandlerTest {
	SqlRequest request;
	SqlResponse response = new SqlResponse(null);
	SqlHandler handler = new ParameterSqlHandler();

	@BeforeEach
	void beforeEach() {
		this.request = Mockito.mock(SqlRequest.class);
		var metadata = Mockito.mock(EntityMetadata.class);
		Mockito.when(metadata.getFieldMap()).thenReturn(new HashMap<>());
		Mockito.when(request.getMetadata()).thenReturn(metadata);
	}

	@Test
	void testHandle1() {
		response.putParam("k1", "v1");
		response.putParam("k2", "v2");
		response.addName("k2");
		response.addName("k1");

		handler.handle(request, response);

		Assertions.assertEquals(List.of(SqlParameter.create("v2"), SqlParameter.create("v1")),
				response.toParameterList());
	}

	@Test
	void testHandle2() {
		response.putParam("k1", "v1");
		response.putParam("k2", "v2");
		response.addName("k2");

		handler.handle(request, response);

		Assertions.assertEquals(List.of(SqlParameter.create("v2")), response.toParameterList());
	}

	@Test
	void testHandle3() {
		response.putParam("k1", "v1");
		response.putParam("k2", "v2");
		response.addName("k3");

		handler.handle(request, response);

		var parameterList = response.toParameterList();
		Assertions.assertEquals(1, parameterList.size());
		Assertions.assertNull(parameterList.get(0).getValue());
	}
}
