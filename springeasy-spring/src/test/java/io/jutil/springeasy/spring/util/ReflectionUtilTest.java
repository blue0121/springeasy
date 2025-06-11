package io.jutil.springeasy.spring.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2025-05-08
 */
class ReflectionUtilTest {

	@Test
	void testGetActualTypeArguments() {
		var child = new Child();
		Assertions.assertArrayEquals(new Class<?>[]{String.class},
				ReflectionUtil.getActualTypeArguments(child));

		var child2 = new Child2();
		Assertions.assertArrayEquals(new Class<?>[]{Integer.class, String.class},
				ReflectionUtil.getActualTypeArguments(child2));
	}

	static class Parent<T> {}

	static class Child extends Parent<String> {}

	static class Base<T, R> {}

	static class Child2 extends Base<Integer, String> {}
}
