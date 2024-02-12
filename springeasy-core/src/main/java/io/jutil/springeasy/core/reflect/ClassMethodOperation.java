package io.jutil.springeasy.core.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface ClassMethodOperation extends MethodOperation {

	ClassOperation getClassOperation();

	Object invoke(Object target, Object... args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	Object invokeQuietly(Object target, Object... args);

}
