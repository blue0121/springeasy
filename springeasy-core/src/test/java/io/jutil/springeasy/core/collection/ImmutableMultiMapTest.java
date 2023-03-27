package io.jutil.springeasy.core.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Jin Zheng
 * @since 2023-03-27
 */
class ImmutableMultiMapTest {

	@Test
	void test1() {
		MultiMap<Integer, Integer> map = MultiMap.create();
		map.put(1, 11);
		map.put(1, 12);
		MultiMap<Integer, Integer> readOnlyMap = MultiMap.copyOf(map);
		Assertions.assertEquals(HashMap.class, readOnlyMap.getMapType());
		Assertions.assertEquals(1, readOnlyMap.size());
		Set<Integer> set = readOnlyMap.get(1);
		Assertions.assertNotNull(set);
		Assertions.assertEquals(2, set.size());
		Assertions.assertEquals(set, Set.of(11, 12));
		Assertions.assertThrows(UnsupportedOperationException.class, () -> readOnlyMap.clear());
		Assertions.assertThrows(UnsupportedOperationException.class, () -> readOnlyMap.put(2, 2));
		Assertions.assertThrows(UnsupportedOperationException.class, () -> readOnlyMap.remove(1));
	}

}
