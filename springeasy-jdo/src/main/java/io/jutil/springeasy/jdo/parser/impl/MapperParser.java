package io.jutil.springeasy.jdo.parser.impl;

import io.jutil.springeasy.core.reflect.ReflectFactory;
import io.jutil.springeasy.jdo.dialect.Dialect;
import io.jutil.springeasy.jdo.parser.ColumnMetadata;
import io.jutil.springeasy.jdo.parser.FieldMetadata;
import io.jutil.springeasy.jdo.parser.MapperMetadata;
import io.jutil.springeasy.jdo.parser.model.DefaultColumnMetadata;
import io.jutil.springeasy.jdo.parser.model.DefaultMapperMetadata;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-08
 */
@Slf4j
public class MapperParser extends AbstractParser {
	public MapperParser(Dialect dialect) {
		super(dialect);
	}

	@Override
	public MapperMetadata parse(Class<?> clazz) {
		var classOperation = ReflectFactory.getClassOperation(clazz);
		var metadata = new DefaultMapperMetadata();
		metadata.setClassOperation(classOperation);
		Map<String, ColumnMetadata> columnMap = new LinkedHashMap<>();
		Map<String, FieldMetadata> fieldMap = new LinkedHashMap<>();

		var fields = classOperation.getAllFields();
		for (var entry : fields.entrySet()) {
			var column = new DefaultColumnMetadata();
			this.setFieldMetadata(entry.getValue(), column);
			columnMap.put(column.getFieldName(), column);
			fieldMap.put(column.getFieldName(), column);
			log.debug("普通字段: {} <==> {}", column.getFieldName(), column.getColumnName());
		}

		metadata.setColumnMap(columnMap);
		metadata.setFieldMap(fieldMap);
		metadata.check();
		return metadata;
	}
}
