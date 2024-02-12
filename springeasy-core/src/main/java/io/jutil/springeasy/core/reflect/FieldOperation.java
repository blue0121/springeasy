package io.jutil.springeasy.core.reflect;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
public interface FieldOperation extends AnnotationOperation, NameOperation {

	String getFieldName();

	Class<?> getType();

	int getModifiers();

	<T extends Annotation> T getGetterAnnotation(Class<?> annotationClass);

	List<Annotation> getGetterAnnotations();

	<T extends Annotation> T getSetterAnnotation(Class<?> annotationClass);

	List<Annotation> getSetterAnnotations();
}
