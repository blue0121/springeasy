package io.jutil.springeasy.core.reflect;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
public class ReflectFactory {
	private static final ConcurrentMap<Class<?>, ClassOperation> CACHE = new ConcurrentHashMap<>();

	private ReflectFactory() {
	}

	/**
	 * 根据目标类型获取缓存中的JavaBean对象，不存在则创建
	 *
	 * @param targetClass
	 * @return
	 */
	public static ClassOperation getClassOperation(Class<?> targetClass) {
		return CACHE.computeIfAbsent(targetClass, DefaultClassOperation::new);
	}

	/**
	 * 创建方法签名对象
	 *
	 * @param name
	 * @param parameterTypes
	 * @return
	 */
	public static MethodSignature createMethodSignature(String name,
	                                                    Class<?>... parameterTypes) {
		return new DefaultMethodSignature(name, parameterTypes);
	}

	/**
	 * 创建方法签名对象
	 *
	 * @param name
	 * @param parameterTypes
	 * @return
	 */
	public static MethodSignature createMethodSignature(String name,
	                                                    Collection<Class<?>> parameterTypes) {
		return new DefaultMethodSignature(name, parameterTypes);
	}

	/**
	 * 创建方法签名对象
	 *
	 * @param method
	 * @return
	 */
	public static MethodSignature createMethodSignature(Method method) {
		return new DefaultMethodSignature(method);
	}
}
