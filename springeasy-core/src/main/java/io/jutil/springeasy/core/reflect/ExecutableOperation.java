package io.jutil.springeasy.core.reflect;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface ExecutableOperation extends AnnotationOperation, NameOperation {

	List<Class<?>> getParameterTypeList();

	List<ParameterOperation> getParameterList();

	int getModifiers();

}
