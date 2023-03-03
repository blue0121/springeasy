package io.jutil.springeasy.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-03-02
 */
class AssertUtilTest {

	@Test
	void testNotNull() {
		AssertUtil.notNull(new Object(), null);
		AssertUtil.notNull(new Object(), "Object");
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notNull(null, "Object"));
	}

	@Test
	void testPositive() {
		AssertUtil.positive(1, null);
		AssertUtil.positive(1, "Number");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.positive(0, "Number"));
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.positive(-1, "Number"));
	}

	@Test
	void testNonNegative() {
		AssertUtil.nonNegative(0, null);
		AssertUtil.nonNegative(0, "Number");
		AssertUtil.nonNegative(1, "Number");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.nonNegative(-1, "Number"));
	}

	@Test
	void testNotEmpty() {
		AssertUtil.notEmpty("str", null);
		AssertUtil.notEmpty("str", "String");
		AssertUtil.notEmpty(List.of(1), null);
		AssertUtil.notEmpty(List.of(1), "List");
		AssertUtil.notEmpty(Map.of(1, 1), null);
		AssertUtil.notEmpty(Map.of(1, 1), "Map");
		AssertUtil.notEmpty(new String[]{"Str"}, null);
		AssertUtil.notEmpty(new String[]{"Str"}, "Array");
		AssertUtil.notEmpty(new byte[]{1}, null);
		AssertUtil.notEmpty(new byte[]{1}, "Byte Array");

		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty("", "String"));
		var list = List.of();
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(list, "List"));

		var map = Map.of();
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(map, "Map"));

		var array = new String[0];
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(array, "Array"));

		var byteArray = new byte[0];
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(byteArray, "Byte Array"));
	}

	@Test
	void testIsTrue() {
		AssertUtil.isTrue(true, "Exp");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.isTrue(false, "Exp"));
	}

	@Test
	void testIsFalse() {
		AssertUtil.isFalse(false, "Exp");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.isFalse(true, "Exp"));
	}

}
