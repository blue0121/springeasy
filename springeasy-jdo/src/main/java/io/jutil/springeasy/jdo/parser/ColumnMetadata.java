package io.jutil.springeasy.jdo.parser;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface ColumnMetadata extends FieldMetadata {

	/**
	 * 是否强制插入空值
	 *
	 * @return
	 */
	boolean isMustInsert();

	/**
	 * 是否强制更新空值
	 *
	 * @return
	 */
	boolean isMustUpdate();
}
