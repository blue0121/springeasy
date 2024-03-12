package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.core.reflect.ClassFieldOperation;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface FieldMetadata {
	/**
	 * 字段名称
	 *
	 * @return
	 */
	String getFieldName();

	/**
	 * 数据表列名
	 *
	 * @return
	 */
	String getColumnName();

	/**
	 * 数据表列定义
	 * @return
	 */
	String getDefinition();

	/**
	 * Bean字段操作
	 *
	 * @return
	 */
	ClassFieldOperation getFieldOperation();
}
