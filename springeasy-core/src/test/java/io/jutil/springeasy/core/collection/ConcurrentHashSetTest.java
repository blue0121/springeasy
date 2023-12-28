package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class ConcurrentHashSetTest {

	@Test
	void test1() {
		Set<Integer> set = new ConcurrentHashSet<>();
		set.add(1);
		set.add(2);
		Assertions.assertEquals(2, set.size());
		set.add(2);
		Assertions.assertEquals(2, set.size());
	}

}
