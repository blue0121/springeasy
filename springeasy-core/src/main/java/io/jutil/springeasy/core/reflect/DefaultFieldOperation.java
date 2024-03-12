package io.jutil.springeasy.core.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
@Slf4j
abstract class DefaultFieldOperation extends DefaultAnnotationOperation implements FieldOperation {
	private final String fieldName;
	protected final Field field;
	private final MethodOperation getterMethod;
	private final MethodOperation setterMethod;

	private Map<Class<?>, Annotation> getterAnnotationMap;
	private List<Annotation> getterAnnotationList;
	private Map<Class<?>, Annotation> setterAnnotationMap;
	private List<Annotation> setterAnnotationList;

	DefaultFieldOperation(String fieldName, Field field,
	                      MethodOperation getterMethod, MethodOperation setterMethod) {
		this.fieldName = fieldName;
		this.field = field;
		this.getterMethod = getterMethod;
		this.setterMethod = setterMethod;
		this.parseAnnotation();
		if (log.isDebugEnabled()) {
			log.debug("字段: {}, {}, Setter方法: {}, Getter方法: {}, 字段注解: {}," +
							" Getter方法注解: {}, Setter方法注解: {}",
					fieldName, field != null, setterMethod != null ? setterMethod.getName() : null,
					getterMethod != null ? getterMethod.getName() : null,
					this.getAnnotations(), getterAnnotationList, setterAnnotationList);
		}
	}

	private void parseAnnotation() {
		Map<Class<?>, Annotation> annotationMap = new HashMap<>();
		if (field != null) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				annotationMap.put(annotation.annotationType(), annotation);
			}
		}
		this.initAnnotationMap(annotationMap);

		var getterMap = this.parseAnnotationWithMethod(annotationMap, getterMethod);
		this.getterAnnotationMap = Map.copyOf(getterMap);
		this.getterAnnotationList = List.copyOf(getterMap.values());

		var setterMap = this.parseAnnotationWithMethod(annotationMap, setterMethod);
		this.setterAnnotationMap = Map.copyOf(setterMap);
		this.setterAnnotationList = List.copyOf(setterMap.values());
	}

	private Map<Class<?>, Annotation> parseAnnotationWithMethod(Map<Class<?>, Annotation> annotationMap,
	                                                            MethodOperation method) {
		Map<Class<?>, Annotation> methodAnnotationMap = new HashMap<>();
		if (method != null) {
			method.getAnnotations().forEach(e -> methodAnnotationMap.put(e.annotationType(), e));
		}
		for (var entry : annotationMap.entrySet()) {
			if (methodAnnotationMap.containsKey(entry.getKey())) {
				continue;
			}

			methodAnnotationMap.put(entry.getKey(), entry.getValue());
		}
		return methodAnnotationMap;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public int getModifiers() {
		return field.getModifiers();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getGetterAnnotation(Class<?> annotationClass) {
		return (T) getterAnnotationMap.get(annotationClass);
	}

	@Override
	public List<Annotation> getGetterAnnotations() {
		return getterAnnotationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getSetterAnnotation(Class<?> annotationClass) {
		return (T) setterAnnotationMap.get(annotationClass);
	}

	@Override
	public List<Annotation> getSetterAnnotations() {
		return setterAnnotationList;
	}

	@Override
	public String getName() {
		return fieldName;
	}
}
