package io.jutil.springeasy.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Jin Zheng
 * @since 1.0 2019-11-15
 */
@Slf4j
public class WaitUtil {

	public static final long DEFAULT_TIMEOUT = 5;
	public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

	private WaitUtil() {
	}

	public static void await(CountDownLatch latch) {
		try {
			latch.await();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("CountDownLatch await timeout");
		}
	}

	public static void await(CountDownLatch latch, long timeout, TimeUnit unit) {
		if (latch == null) {
			return;
		}

		if (timeout <= 0) {
			timeout = DEFAULT_TIMEOUT;
		}
		if (unit == null) {
			unit = DEFAULT_TIME_UNIT;
		}
		try {
			var rs = latch.await(timeout, unit);
			log.debug("CountDownLatch await result: {}", rs);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("CountDownLatch await timeout");
		}
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("Interrupted");
		}
	}

	public static void sleep(long time, TimeUnit unit) {
		try {
			Thread.sleep(unit.toMillis(time));
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("Interrupted");
		}

	}

}
