package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.core.reflect.ClassFieldOperation;
import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jdo.parser.FieldMetadata;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Getter
public abstract class DefaultFieldMetadata implements FieldMetadata {
	protected String fieldName;
	protected String columnName;
	protected String definition;
	protected ClassFieldOperation fieldOperation;

	protected DefaultFieldMetadata() {
	}

	public void check() {
		AssertUtil.notEmpty(fieldName, "字段名");
		AssertUtil.notEmpty(columnName, "列名");
		AssertUtil.notNull(fieldOperation, "字段操作");
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setBeanField(ClassFieldOperation fieldOperation) {
		AssertUtil.notNull(fieldOperation, "字段");
		this.fieldName = fieldOperation.getFieldName();
		this.fieldOperation = fieldOperation;
	}

}
