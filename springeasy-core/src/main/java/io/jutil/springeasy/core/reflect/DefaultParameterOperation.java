package io.jutil.springeasy.core.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
@Slf4j
class DefaultParameterOperation extends DefaultAnnotationOperation implements ParameterOperation {
	private final Parameter parameter;
	private final List<Parameter> parameterList;

	DefaultParameterOperation(List<Parameter> parameterList) {
		super(parameterList.getFirst());
		this.parameter = parameterList.getFirst();
		this.parameterList = parameterList;
		this.parseAnnotation();
		if (log.isDebugEnabled()) {
			log.debug("参数名称: {}, 注解: {}", this.getName(), this.getAnnotations());
		}
	}

	private void parseAnnotation() {
		Map<Class<?>, Annotation> annotationMap = new HashMap<>();
		for (var param : parameterList) {
			for (var annotation : param.getDeclaredAnnotations()) {
				if (annotationMap.containsKey(annotation.annotationType())) {
					continue;
				}

				annotationMap.put(annotation.annotationType(), annotation);
			}
		}

		this.initAnnotationMap(annotationMap);
	}

	@Override
	public String getName() {
		return parameter.getName();
	}

	@Override
	public Class<?> getType() {
		return parameter.getType();
	}
}
