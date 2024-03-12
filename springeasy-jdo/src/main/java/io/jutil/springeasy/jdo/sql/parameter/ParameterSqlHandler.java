package io.jutil.springeasy.jdo.sql.parameter;

import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlParameter;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class ParameterSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var fieldMap = request.getMetadata().getFieldMap();
		var list = response.toNameList();
		var map = response.toParamMap();
		for (var name : list) {
			var value = map.get(name);
			var field = fieldMap.get(name);
			var parameter = SqlParameter.create(field, value);
			response.addParameter(parameter);
		}
	}
}
