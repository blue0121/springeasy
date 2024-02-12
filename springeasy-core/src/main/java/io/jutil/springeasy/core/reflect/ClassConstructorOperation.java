package io.jutil.springeasy.core.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface ClassConstructorOperation extends ExecutableOperation {

	ClassOperation getClassOperation();

	Object newInstance(Object... args) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	Object newInstanceQuietly(Object... args);

}
