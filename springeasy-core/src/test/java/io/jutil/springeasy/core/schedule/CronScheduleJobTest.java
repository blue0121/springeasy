package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.mutex.memory.MemoryMutexFactory;
import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jin Zheng
 * @since 2024-03-01
 */
class CronScheduleJobTest {
	final ExecutorService executor = Executors.newFixedThreadPool(2,
			new ExecutorThreadFactory("springeasy"));

	final String jobId = "job1";
	final String jobCron = "0/1 * * * * *";

	@Test
	void testSchedule() {
		var facade = Mockito.mock(TestFacade.class);
		var mutexFactory = new MemoryMutexFactory();
		var schedule = new CronScheduleJob(executor, mutexFactory);
		var job = new TestJob(facade);
		schedule.add(jobId, jobCron, job);
		WaitUtil.sleep(1500);

		Mockito.verify(facade, Mockito.atLeast(1)).test();

		schedule.remove(jobId);
		WaitUtil.sleep(1500);
		Mockito.verify(facade, Mockito.atMost(2)).test();
	}

	interface TestFacade {
		void test();
	}

	@Slf4j
	static class TestJob implements ScheduleJob {
		TestFacade facade;

		public TestJob(TestFacade facade) {
			this.facade = facade;
		}

		@Override
		public void run(ScheduleContext ctx) {
			log.info("RunJob, id: {}, cron: {}", ctx.getId(), ctx.getCron());
			facade.test();
		}
	}
}
