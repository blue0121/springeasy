package io.jutil.springeasy.jdo.parser;

import io.jutil.springeasy.jdo.annotation.GeneratorType;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface IdMetadata extends FieldMetadata {

	/**
	 * 主键类型
	 *
	 * @return
	 */
	IdType getIdType();

	/**
	 * 主键生成类型
	 *
	 * @return
	 */
	GeneratorType getGeneratorType();

}
