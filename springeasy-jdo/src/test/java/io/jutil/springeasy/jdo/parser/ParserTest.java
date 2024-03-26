package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.jdo.annotation.GeneratorType;
import org.junit.jupiter.api.Assertions;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
abstract class ParserTest {
	protected void checkTable(EntityMetadata metadata, String table) {
		Assertions.assertNotNull(metadata);
		Assertions.assertEquals(table, metadata.getTableName());
	}

	protected void checkId(IdMetadata metadata, IdType idType, GeneratorType generatorType,
	                       String field, String column) {
		this.checkField(metadata, field, column);
		Assertions.assertEquals(idType, metadata.getIdType());
		Assertions.assertEquals(generatorType, metadata.getGeneratorType());
	}

	protected void checkColumn(ColumnMetadata metadata, boolean mustInsert, boolean mustUpdate,
							   boolean nullable, String field, String column, String definition) {
		this.checkField(metadata, field, column);
		Assertions.assertEquals(mustInsert, metadata.isMustInsert());
		Assertions.assertEquals(mustUpdate, metadata.isMustUpdate());
		Assertions.assertEquals(nullable, metadata.isNullable());
		Assertions.assertEquals(definition, metadata.getDefinition());
	}

	protected void checkVersion(VersionMetadata metadata, boolean force, int defaultValue,
	                            String field, String column) {
		this.checkField(metadata, field, column);
		Assertions.assertEquals(force, metadata.isForce());
		Assertions.assertEquals(defaultValue, metadata.getDefaultValue());
	}

	protected void checkField(FieldMetadata metadata, String field, String column) {
		Assertions.assertNotNull(metadata);
		Assertions.assertEquals(field, metadata.getFieldName());
		Assertions.assertEquals(column, metadata.getColumnName());
	}

	protected void checkSql(SqlItem item, String sql, List<String> parameterNameList) {
		Assertions.assertNotNull(item);
		Assertions.assertEquals(sql, item.getSql());
		Assertions.assertEquals(parameterNameList, item.getParameterNameList());
	}
}
