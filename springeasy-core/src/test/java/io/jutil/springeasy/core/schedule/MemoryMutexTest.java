package io.jutil.springeasy.core.schedule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Jin Zheng
 * @since 2023-08-13
 */
class MemoryMutexTest {
	MutexFactory factory = new MemoryMutexFactory();

	final String id = "id";

	@Test
	void testMutex() {
		var mutex = factory.create(id);
		Assertions.assertEquals(id, mutex.getId());
		Assertions.assertTrue(mutex.tryLock());
		Assertions.assertFalse(mutex.tryLock());
		mutex.unlock();
		Assertions.assertTrue(mutex.tryLock());
		Assertions.assertFalse(mutex.tryLock());
		mutex.unlock();
	}

	@Test
	void testMutexFactory() {
		var mutex1 = factory.create(id);
		var mutex2 = factory.create(id);
		Assertions.assertNotSame(mutex1, mutex2);
	}
}
