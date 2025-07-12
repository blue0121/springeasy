package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.redis.RedisTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@SuppressWarnings("java:S2699")
class RedisMutexIT extends RedisTest {
	@Autowired
	MutexFactory factory;

	@Autowired
	ExecutorService executor;

	final String lockKey = "redisLock";

	@Disabled
	@Test
	void testMutex() {
		var counter = new AtomicInteger(0);
		var latch = new CountDownLatch(2);
		var mutex = factory.create("redisMutex");
		var job1 = new Job(counter, mutex, latch);
		var job2 = new Job(counter, mutex, latch);
		executor.submit(job1);
		executor.submit(job2);
		WaitUtil.await(latch);
		Assertions.assertEquals(1, counter.get());
	}

	@Test
	void testTryLock() throws Exception {
		var mutex = (RedisMutex) factory.create(lockKey);
		Assertions.assertTrue(mutex.tryLock());
		Assertions.assertFalse(mutex.tryLock());
		mutex.unlock();
		Assertions.assertTrue(mutex.tryLock());
		mutex.unlock();
	}

	@Disabled
	@Test
	void testExecute() {
		var mutex = (RedisMutex) factory.create("redisLock");
		mutex.execute(() -> {
			System.out.println(Thread.currentThread().getName());
			return null;
		});
	}

	class Job implements Runnable {
		private final AtomicInteger counter;
		private final Mutex mutex;
		private final CountDownLatch latch;

		public Job(AtomicInteger counter, Mutex mutex, CountDownLatch latch) {
			this.counter = counter;
			this.mutex = mutex;
			this.latch = latch;
		}

		@Override
		public void run() {
			if (mutex.tryLock()) {
				WaitUtil.sleep(500);
				counter.incrementAndGet();
				mutex.unlock();
			}
			latch.countDown();
		}
	}
}
