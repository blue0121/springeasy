package io.jutil.springeasy.redis.mutex;

import io.jutil.springeasy.core.schedule.Mutex;
import io.jutil.springeasy.core.schedule.MutexFactory;
import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.redis.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jin Zheng
 * @since 2024-05-17
 */
@SpringBootTest(classes = Application.class)
class RedisMutexIT {
	@Autowired
	MutexFactory factory;

	@Autowired
	ExecutorService executor;

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
	void testLock() {
		var mutex = (RedisMutex) factory.create("redisLock");
		mutex.lock(5, TimeUnit.SECONDS);
		System.out.println(Thread.currentThread().getName());
		mutex.unlock();
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
