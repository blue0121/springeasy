package io.jutil.springeasy.core.reflect;

import io.jutil.springeasy.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
@Slf4j
class DefaultClassMethodOperation extends DefaultMethodOperation implements ClassMethodOperation {
	private final ClassOperation classOperation;

	DefaultClassMethodOperation(ClassOperation classOperation, Method method,
	                            List<Class<?>> superClassList, List<Class<?>> interfaceList) {
		super(method, superClassList, interfaceList);
		this.classOperation = classOperation;
	}

	@Override
	public ClassOperation getClassOperation() {
		return classOperation;
	}

	@Override
	public Object invoke(Object target, Object... args) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		AssertUtil.notNull(target, "目标对象");
		return method.invoke(target, args);
	}

	@Override
	public Object invokeQuietly(Object target, Object... args) {
		Object value = null;
		try {
			value = this.invoke(target, args);
		}
		catch (Exception e) {
			log.error("调用方法错误,", e);
		}
		return value;
	}
}
