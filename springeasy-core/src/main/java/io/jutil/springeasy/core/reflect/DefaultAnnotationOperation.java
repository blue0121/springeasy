package io.jutil.springeasy.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
class DefaultAnnotationOperation implements AnnotationOperation {
	private final AnnotatedElement annotatedElement;
	private Map<Class<?>, Annotation> annotationMap;
	private List<Annotation> annotationList;

	DefaultAnnotationOperation(AnnotatedElement annotatedElement) {
		this.annotatedElement = annotatedElement;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return (T) annotationMap.get(annotationClass);
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annotationList;
	}

	protected final void initAnnotationMap(Map<Class<?>, Annotation> annotationMap) {
		this.annotationMap = Map.copyOf(annotationMap);
		this.annotationList = List.copyOf(annotationMap.values());
	}
}
