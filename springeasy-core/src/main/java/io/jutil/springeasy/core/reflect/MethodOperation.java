package io.jutil.springeasy.core.reflect;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface MethodOperation extends ExecutableOperation {

	MethodSignature getMethodSignature();

	Class<?> getReturnType();

	String getRepresentFieldName();

	boolean isSetter();

	boolean isGetter();

}
