package io.jutil.springeasy.jdo.sql;

import io.jutil.springeasy.jdo.exception.EntityFieldException;
import io.jutil.springeasy.jdo.parser.ColumnMetadata;
import io.jutil.springeasy.jdo.parser.FieldMetadata;
import io.jutil.springeasy.jdo.parser.IdMetadata;
import io.jutil.springeasy.jdo.parser.VersionMetadata;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-08
 */
@NoArgsConstructor
public abstract class AbstractSqlHandler implements SqlHandler {


	public ColumnMetadata getColumn(String name, Map<String, ColumnMetadata> columnMap) {
		var column = columnMap.get(name);
		if (column == null) {
			throw new EntityFieldException(name);
		}
		return column;
	}

	public String getColumnString(String name, Map<String, IdMetadata> idMap, Map<String, ColumnMetadata> columnMap,
	                              VersionMetadata version) {
		if (columnMap != null && columnMap.get(name) != null) {
			return columnMap.get(name).getColumnName();
		}
		if (idMap != null && idMap.get(name) != null) {
			return idMap.get(name).getColumnName();
		}
		if (version != null && version.getFieldName().equals(name)) {
			return version.getColumnName();
		}
		throw new EntityFieldException(name);
	}

	protected void putParam(SqlRequest request, SqlResponse response, FieldMetadata config) {
		var field = config.getFieldName();
		var beanField = config.getFieldOperation();
		var value = beanField.getFieldValue(request.getTarget());
		if (request.isDynamic() && this.isEmpty(value)) {
			response.removeParam(field);
		} else {
			response.putParam(field, value);
		}
	}

	protected boolean isEmpty(Object value) {
		return value == null || "".equals(value);
	}

}
