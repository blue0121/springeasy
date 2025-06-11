package io.jutil.springeasy.spring.util;

import org.springframework.aop.support.AopUtils;

import java.lang.reflect.ParameterizedType;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
public class ReflectionUtil {
	private ReflectionUtil() {
	}

	public static Class<?>[] getActualTypeArguments(Object object) {
		var clazz = AopUtils.getTargetClass(object);
		var types = ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments();
		var classes = new Class<?>[types.length];
		for (int i = 0; i < types.length; i++) {
			classes[i] = (Class<?>) types[i];
		}
		return classes;
	}

}
