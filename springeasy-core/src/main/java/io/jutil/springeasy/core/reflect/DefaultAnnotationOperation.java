package io.jutil.springeasy.core.reflect;

import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
@NoArgsConstructor
class DefaultAnnotationOperation implements AnnotationOperation {
	private Map<Class<?>, Annotation> annotationMap;
	private List<Annotation> annotationList;


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
