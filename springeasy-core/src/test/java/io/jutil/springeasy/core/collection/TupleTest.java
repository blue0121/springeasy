package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-06-04
 */
class TupleTest {

	@Test
	void test1() {
		var tuple = new Tuple(1, 2, 3);
		Assertions.assertEquals(3, tuple.getSize());
		Assertions.assertEquals(1, (Integer) tuple.getFirst());
		Assertions.assertEquals(2, (Integer) tuple.getSecond());
		Assertions.assertEquals(3, (Integer) tuple.getThird());
	}

	@Test
	void test2() {
		var tuple = new Tuple(1, 2);
		Assertions.assertEquals(2, tuple.getSize());
		Assertions.assertEquals(1, (Integer) tuple.getFirst());
		Assertions.assertEquals(2, (Integer) tuple.getSecond());
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, tuple::getThird);
	}
}
