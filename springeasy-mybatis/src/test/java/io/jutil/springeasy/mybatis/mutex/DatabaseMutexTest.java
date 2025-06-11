package io.jutil.springeasy.mybatis.mutex;

import io.jutil.springeasy.core.mutex.RenewSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@ExtendWith(MockitoExtension.class)
class DatabaseMutexTest {
	@Mock
	SqlExecutor executor;
	@Mock
	RenewSchedule schedule;

	DatabaseMutex mutex;

	final String key = "key123";
	final String instanceId = UUID.randomUUID().toString();

	@BeforeEach
	void beforeEach() {
		Mockito.when(schedule.getExpireSec()).thenReturn(10);
		Mockito.when(schedule.getInstanceId()).thenReturn(instanceId);

		mutex = new DatabaseMutex(executor, key, schedule);
	}

	@Test
	void testGetter() {
		Assertions.assertNotNull(mutex.getKey());
	}

	@Test
	void testTryLock1() {
		Mockito.when(executor.canStart(Mockito.eq(key), Mockito.eq(instanceId), Mockito.any()))
				.thenReturn(true);
		Assertions.assertTrue(mutex.tryLock());
		Mockito.verify(schedule).startRenew(Mockito.anyString());
	}

	@Test
	void testTryLock2() {
		Mockito.when(executor.canStart(Mockito.eq(key), Mockito.eq(instanceId), Mockito.any()))
				.thenReturn(false);
		Assertions.assertFalse(mutex.tryLock());
		Mockito.verify(schedule, Mockito.never()).startRenew(Mockito.anyString());
	}

	@Test
	void testUnlock1() {
		mutex.unlock();
		Mockito.verify(executor).delete(Mockito.anyString());
		Mockito.verify(schedule).stopRenew(Mockito.anyString());
	}

}
