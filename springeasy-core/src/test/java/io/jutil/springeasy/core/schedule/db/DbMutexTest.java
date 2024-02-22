package io.jutil.springeasy.core.schedule.db;

import io.jutil.springeasy.core.schedule.impl.RenewSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.UUID;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
class DbMutexTest {
	SqlExecutor executor;
	RenewSchedule schedule;
	DbMutex mutex;

	final String jobId = "jobId123";
	final String instanceId = UUID.randomUUID().toString();

	@BeforeEach
	void beforeEach() {
		executor = Mockito.mock(SqlExecutor.class);
		schedule = Mockito.mock(RenewSchedule.class);

		mutex = new DbMutex();
		mutex.setSqlExecutor(executor);
		mutex.setRenewSchedule(schedule);
		mutex.setExpireSec(10);
		mutex.setJobId(jobId);
		mutex.setInstanceId(instanceId);
		mutex.setJobIdSet(Set.of(jobId));
	}

	@Test
	void testGetter() {
		Assertions.assertNotNull(mutex.getSqlExecutor());
		Assertions.assertNotNull(mutex.getRenewSchedule());
		Assertions.assertNotNull(mutex.getExpireSec());
		Assertions.assertNotNull(mutex.getJobId());
		Assertions.assertNotNull(mutex.getInstanceId());
		Assertions.assertNotNull(mutex.getJobIdSet());
	}

	@Test
	void testTryLock1() {
		Mockito.when(executor.canStart(Mockito.eq(jobId), Mockito.eq(instanceId), Mockito.any()))
				.thenReturn(true);
		Assertions.assertTrue(mutex.tryLock());
		Mockito.verify(schedule).startRenew(Mockito.anyString());
	}

	@Test
	void testTryLock2() {
		Mockito.when(executor.canStart(Mockito.eq(jobId), Mockito.eq(instanceId), Mockito.any()))
				.thenReturn(false);
		Assertions.assertFalse(mutex.tryLock());
		Mockito.verify(schedule, Mockito.never()).startRenew(Mockito.anyString());
	}

	@Test
	void testUnlock1() {
		mutex.unlock();
		Mockito.verify(executor).finish(Mockito.anyString());
		Mockito.verify(schedule).stopRenew(Mockito.anyString());
		Mockito.verify(executor, Mockito.never()).delete(Mockito.anyString());
	}

	@Test
	void testUnlock2() {
		mutex.setJobIdSet(Set.of());
		mutex.unlock();
		Mockito.verify(executor).delete(Mockito.anyString());
		Mockito.verify(schedule).stopRenew(Mockito.anyString());
		Mockito.verify(executor, Mockito.never()).finish(Mockito.anyString());
	}
}
