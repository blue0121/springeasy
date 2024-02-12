package io.jutil.springeasy.core.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface ClassOperation extends AnnotationOperation, NameOperation {

	Class<?> getTargetClass();

	ClassConstructorOperation getConstructor(Class<?>... types);

	Object newInstance(Object... args) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	Object newInstanceQuietly(Object... args);

	List<ClassMethodOperation> getAllMethods();

	ClassMethodOperation getMethod(MethodSignature methodSignature);

	Map<String, ClassFieldOperation> getAllFields();

	ClassFieldOperation getField(String fieldName);

	Map<String, Object> getAllFieldValues(Object target);

}
