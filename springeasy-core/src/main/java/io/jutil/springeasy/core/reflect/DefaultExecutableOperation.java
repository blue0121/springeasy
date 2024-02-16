package io.jutil.springeasy.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
abstract public class DefaultExecutableOperation extends DefaultAnnotationOperation
		implements ExecutableOperation {
	private final Executable executable;
	private final List<Class<?>> superClassList;
	private final List<Class<?>> interfaceList;

	protected List<Executable> superExecutableList;
	protected List<Class<?>> parameterTypeList;
	protected List<ParameterOperation> parameterList;

	DefaultExecutableOperation(Executable executable, List<Class<?>> superClassList, List<Class<?>> interfaceList) {
		super(executable);
		this.executable = executable;
		this.superClassList = superClassList;
		this.interfaceList = interfaceList;
		this.parse();
		this.parseAnnotation();
		this.parseParam();
	}

	protected void parse() {
		var paramClasses = executable.getParameterTypes();
		List<Class<?>> paramList = new ArrayList<>();
		for (var paramClass : paramClasses) {
			paramList.add(paramClass);
		}
		this.parameterTypeList = List.copyOf(paramList);
	}

	protected void parseAnnotation() {
		List<Executable> executableList = new ArrayList<>();
		Map<Class<?>, Annotation> annotationMap = new HashMap<>();
		this.parseAnnotation(executable, executableList, annotationMap);
		this.parseAnnotation(superClassList, executableList, annotationMap);
		this.parseAnnotation(interfaceList, executableList, annotationMap);

		this.superExecutableList = List.copyOf(executableList);
		this.initAnnotationMap(annotationMap);
	}

	protected void parseAnnotation(List<Class<?>> classList, List<Executable> executableList,
	                             Map<Class<?>, Annotation> annotationMap) {
		if (classList != null && !classList.isEmpty()) {
			for (var clazz : classList) {
				Executable destination = this.findExecutable(executable, clazz);
				if (destination == null) {
					continue;
				}

				this.parseAnnotation(destination, executableList, annotationMap);
			}
		}
	}

	protected void parseAnnotation(Executable executable, List<Executable> executableList,
	                               Map<Class<?>, Annotation> annotationMap) {
		executableList.add(executable);
		Annotation[] annotations = executable.getDeclaredAnnotations();
		for (var annotation : annotations) {
			if (annotationMap.containsKey(annotation.annotationType())) {
				continue;
			}

			annotationMap.put(annotation.annotationType(), annotation);
		}
	}

	protected void parseParam() {
		int count = executable.getParameterCount();
		List<ParameterOperation> paramList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			List<Parameter> list = new ArrayList<>();
			for (var superMethod : superExecutableList) {
				var params = superMethod.getParameters();
				list.add(params[i]);
			}
			var param = new DefaultParameterOperation(list);
			paramList.add(param);
		}
		this.parameterList = List.copyOf(paramList);
	}



	@Override
	public final List<Class<?>> getParameterTypeList() {
		return parameterTypeList;
	}

	@Override
	public final List<ParameterOperation> getParameterList() {
		return parameterList;
	}

	@Override
	public final int getModifiers() {
		return executable.getModifiers();
	}

	@Override
	public String getName() {
		return executable.getName();
	}

	protected abstract Executable findExecutable(Executable src, Class<?> clazz);
}
