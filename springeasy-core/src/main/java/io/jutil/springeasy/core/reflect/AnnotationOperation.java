package io.jutil.springeasy.core.reflect;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
public interface AnnotationOperation {

	<T extends Annotation> T getAnnotation(Class<T> annotationClass);

	List<Annotation> getAnnotations();
}
