package io.jutil.springeasy.core.reflect;

import io.jutil.springeasy.core.util.AssertUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Jin Zheng
 * @since 2024-02-16
 */
class DefaultMethodSignature implements MethodSignature {
	private final String name;
	private final Class<?>[] parameterTypes;

	DefaultMethodSignature(String name, Class<?>... parameterTypes) {
		AssertUtil.notEmpty(name, "方法名称");
		AssertUtil.notNull(parameterTypes, "方法参数类型");
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	DefaultMethodSignature(String name, Collection<Class<?>> parameterTypes) {
		AssertUtil.notEmpty(name, "方法名称");
		AssertUtil.notNull(parameterTypes, "方法参数类型");
		this.name = name;
		this.parameterTypes = parameterTypes.toArray(new Class<?>[0]);
	}

	public DefaultMethodSignature(Method method) {
		AssertUtil.notNull(method, "方法");
		this.name = method.getName();
		this.parameterTypes = method.getParameterTypes();
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DefaultMethodSignature that = (DefaultMethodSignature) o;
		return name.equals(that.name) && Arrays.equals(parameterTypes, that.parameterTypes);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(name);
		result = 31 * result + Arrays.hashCode(parameterTypes);
		return result;
	}
}
