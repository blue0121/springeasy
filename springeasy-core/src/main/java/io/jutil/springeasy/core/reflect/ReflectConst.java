package io.jutil.springeasy.core.reflect;

import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2024-2-7 0007
 */
class ReflectConst {
	static final Set<String> IGNORE_METHOD_SET = Set.of("wait", "equals", "toString",
			"hashCode", "getClass", "notify", "notifyAll");

	private ReflectConst() {
	}

	public static boolean isIgnore(String method) {
		return IGNORE_METHOD_SET.contains(method);
	}
}
