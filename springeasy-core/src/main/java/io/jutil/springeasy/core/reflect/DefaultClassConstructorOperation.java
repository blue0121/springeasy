package io.jutil.springeasy.core.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
@Slf4j
class DefaultClassConstructorOperation extends DefaultExecutableOperation implements ClassConstructorOperation {
	private final ClassOperation classOperation;
	private final Constructor<?> constructor;

	DefaultClassConstructorOperation(ClassOperation classOperation, Constructor<?> constructor,
	                                 List<Class<?>> superClassList) {
		super(constructor, superClassList, null);
		this.classOperation = classOperation;
		this.constructor = constructor;
		if (log.isDebugEnabled()) {
			log.debug("构造方法, 参数类型: {}, 注解: {}", this.getParameterTypeList(), this.getAnnotations());
		}
	}

	@Override
	public ClassOperation getClassOperation() {
		return classOperation;
	}

	@Override
	public Object newInstance(Object... args) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (!constructor.canAccess(null)) {
			constructor.setAccessible(true);
		}
		return constructor.newInstance(args);
	}

	@Override
	public Object newInstanceQuietly(Object... args) {
		Object value = null;
		try {
			value = this.newInstance(args);
		}
		catch (Exception e) {
			log.error("实例化对象错误,", e);
		}
		return value;
	}

	@Override
	protected Executable findExecutable(Executable src, Class<?> clazz) {
		if (clazz.isInterface()) {
			return null;
		}

		for (var cst : clazz.getConstructors()) {
			if (Arrays.equals(src.getParameterTypes(), cst.getParameterTypes())) {
				return cst;
			}
		}
		return null;
	}
}
