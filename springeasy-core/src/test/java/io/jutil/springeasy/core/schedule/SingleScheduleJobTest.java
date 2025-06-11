package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.mutex.Mutex;
import io.jutil.springeasy.core.mutex.MutexFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Jin Zheng
 * @since 2024-02-21
 */
@ExtendWith(MockitoExtension.class)
class SingleScheduleJobTest {
	@Mock
	ScheduleContext ctx;
	@Mock
	MutexFactory factory;
	@Mock
	Mutex mutex;

	private final String id = "id";

	@BeforeEach
	void beforeEach() {
		Mockito.when(ctx.getId()).thenReturn(id);
		Mockito.when(ctx.getMutexFactory()).thenReturn(factory);
		Mockito.when(factory.create(Mockito.anyString())).thenReturn(mutex);
	}

	@Test
	void testRun1() {
		Mockito.when(mutex.tryLock()).thenReturn(true);
		var job = new Job();
		job.run(ctx);
		Assertions.assertTrue(job.run);
	}

	@Test
	void testRun2() {
		var job = new Job();
		job.run(ctx);
		Assertions.assertFalse(job.run);
	}

	class Job extends SingleScheduleJob {
		boolean run = false;

		@Override
		protected void runInternal(ScheduleContext ctx) {
			run = true;
		}
	}
}
