package io.jutil.springeasy.jdo.sql.map;

import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class InsertVersionSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var version = request.getMetadata().getVersionMetadata();
		if (version == null) {
			return;
		}

		response.setForceVersion(version.isForce());
		var map = request.getMap();
		Object value = null;
		if (map == null) {
			var beanField = version.getFieldOperation();
			value = beanField.getFieldValue(request.getTarget());
			if (value == null) {
				value = version.getDefaultValue();
				beanField.setFieldValue(request.getTarget(), value);
			}
		} else {
			var field = version.getFieldName();
			value = map.get(field);
			if (value == null) {
				value = version.getDefaultValue();
			}
		}
		response.putParam(version.getFieldName(), value);
	}
}
