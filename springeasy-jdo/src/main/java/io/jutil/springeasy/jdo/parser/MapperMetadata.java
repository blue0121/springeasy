package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.core.reflect.ClassOperation;

import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface MapperMetadata {
	/**
	 * 元数据类型
	 *
	 * @return
	 */
	MetadataType getMetadataType();

	/**
	 * 目标类型
	 *
	 * @return
	 */
	Class<?> getTargetClass();

	/**
	 * Class类操作
	 *
	 * @return
	 */
	ClassOperation getClassOperation();

	/**
	 * 所有普通字段元数据
	 *
	 * @return Map<字段名, 普通字段元数据>
	 */
	Map<String, ColumnMetadata> getColumnMap();

	/**
	 * 所有字段元数据，包括IdMetadata, ColumnMetadata, VersionMetadata, TransientMetadata
	 *
	 * @return Map<字段名, 字段元数据>
	 */
	Map<String, FieldMetadata> getFieldMap();
}
