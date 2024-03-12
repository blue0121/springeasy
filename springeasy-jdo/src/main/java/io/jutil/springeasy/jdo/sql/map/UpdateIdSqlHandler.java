package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.exception.EntityFieldException;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class UpdateIdSqlHandler extends IdSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		super.handle(request, response);

		var config = request.getMetadata();
		var idMap = config.getIdMap();
		var param = response.toParamMap();
		for (var entry : idMap.entrySet()) {
			var value = param.get(entry.getKey());
			if (this.isEmpty(value)) {
				throw new EntityFieldException(entry.getKey(), "不能为空");
			}
		}
	}
}
