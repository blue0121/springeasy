package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.core.id.IdGeneratorFactory;
import io.jutil.springeasy.jdo.exception.EntityFieldException;
import io.jutil.springeasy.jdo.parser.IdMetadata;
import io.jutil.springeasy.jdo.parser.IdType;
import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class InsertIdSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var idMap = request.getMetadata().getIdMap();
		for (var entry : idMap.entrySet()) {
			var id = entry.getValue();
			var field = id.getFieldName();
			switch (id.getGeneratorType()) {
				case UUID -> this.handleUuid(response, field, id.getIdType());
				case SNOWFLAKE -> this.handleSnowflake(response, field, id.getIdType());
				case ASSIGNED -> this.handleAssigned(request, response, id);
				default -> throw new UnsupportedOperationException("不支持主键产生类型: " + id.getGeneratorType());
			}
		}
	}

	private void handleUuid(SqlResponse response, String field, IdType idType) {
		switch (idType) {
			case STRING -> response.putParam(field, IdGeneratorFactory.string20());
			default -> throw new UnsupportedOperationException("不支持主键类型: " + idType);
		}
	}

	private void handleSnowflake(SqlResponse response, String field, IdType idType) {
		switch (idType) {
			case LONG -> response.putParam(field, IdGeneratorFactory.longId());
			default -> throw new UnsupportedOperationException("不支持主键类型: " + idType);
		}
	}

	private void handleAssigned(SqlRequest request, SqlResponse response, IdMetadata id) {
		var map = request.getMap();
		Object value = null;
		if (map == null) {
			var beanField = id.getFieldOperation();
			value = beanField.getFieldValue(request.getTarget());
		} else {
			value = map.get(id.getFieldName());
		}
		if (this.isEmpty(value)) {
			throw new EntityFieldException(id.getFieldName(), "不能为空");
		}

		response.putParam(id.getFieldName(), value);
	}
}
