package io.jutil.springeasy.jdo.parser;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface VersionMetadata extends FieldMetadata {

	/**
	 * 是否强制
	 *
	 * @return
	 */
	boolean isForce();

	/**
	 * 默认值
	 *
	 * @return
	 */
	int getDefaultValue();
}
