package io.jutil.springeasy.core.cache;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-03-26
 */
class SingletonTest {
	@BeforeEach
	void beforeEach() {
		Singleton.clear();
	}

	@Test
	void testGet() {
		Group group1 = Singleton.get(Group.class, k -> new Group());
		Assertions.assertEquals(1, Singleton.size());
		Group group2 = Singleton.get(Group.class, k -> new Group());
		Assertions.assertEquals(1, Singleton.size());
		Assertions.assertSame(group1, group2);
	}

	@Test
	void testPut() {
		Group group1 = new Group();
		Group group2 = new Group();

		Singleton.put(group1);
		Assertions.assertEquals(1, Singleton.size());
		Assertions.assertThrows(IllegalArgumentException.class, () -> Singleton.put(group2));
		Singleton.put(group1);
		Assertions.assertEquals(1, Singleton.size());
	}

	@Test
	void testRemove() {
		Group group1 = new Group();
		Singleton.put(group1);
		Assertions.assertEquals(1, Singleton.size());
		Singleton.remove(group1.getClass());
		Assertions.assertEquals(0, Singleton.size());
		Singleton.put(group1);
		Assertions.assertEquals(1, Singleton.size());
		Singleton.remove(group1.getClass());
		Assertions.assertEquals(0, Singleton.size());
	}

	@NoArgsConstructor
	static class Group {
	}
}
