package io.jutil.springeasy.core.reflect;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface ClassFieldOperation extends FieldOperation {

	ClassOperation getClassOperation();

	ClassMethodOperation getGetterMethod();

	ClassMethodOperation getSetterMethod();

	/**
	 * 获取字段值步骤：
	 * 1. 调用Getter方法<br/>
	 * 2. field.setAccessible(true) & field.get(target)
	 */
	Object getFieldValue(Object target);

	/**
	 * 获取字段值步骤：
	 * 1. 调用Setter方法<br/>
	 * 2. field.setAccessible(true) & field.set(target, value)
	 */
	boolean setFieldValue(Object target, Object value);

}
