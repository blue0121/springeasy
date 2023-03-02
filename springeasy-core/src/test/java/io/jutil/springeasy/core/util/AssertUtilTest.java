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
		AssertUtil.notNull(new Object(), "Object");
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notNull(null, "Object"));
	}

	@Test
	void testPositive() {
		AssertUtil.positive(1, "Number");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.positive(0, "Number"));
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.positive(-1, "Number"));
	}

	@Test
	void testNonNegative() {
		AssertUtil.nonNegative(0, "Number");
		AssertUtil.nonNegative(1, "Number");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> AssertUtil.nonNegative(-1, "Number"));
	}

	@Test
	void testNotEmpty() {
		AssertUtil.notEmpty("str", "String");
		AssertUtil.notEmpty(List.of(1), "List");
		AssertUtil.notEmpty(Map.of(1, 1), "Map");
		AssertUtil.notEmpty(new String[]{"Str"}, "Array");
		AssertUtil.notEmpty(new byte[]{1}, "Byte Array");

		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty("", "String"));
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(List.of(), "List"));
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(Map.of(), "Map"));
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(new String[0], "Array"));
		Assertions.assertThrows(NullPointerException.class,
				() -> AssertUtil.notEmpty(new byte[0], "Byte Array"));
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
