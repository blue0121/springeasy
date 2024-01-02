package io.jutil.springeasy.core.schedule;

import io.jutil.springeasy.core.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jin Zheng
 * @since 2023-08-14
 */
class CronScheduleTest {
	final ExecutorService executor = Executors.newFixedThreadPool(2,
			new ExecutorThreadFactory("springeasy"));

	final String jobId = "job1";
	final String jobCron = "0/1 * * * * *";

	@Test
	void testSchedule() {
		var facade = Mockito.mock(TestFacade.class);
		var schedule = new CronSchedule(executor);
		var job = new TestJob(facade);
		schedule.add(jobId, jobCron, job);
		WaitUtil.sleep(1500);

		Mockito.verify(facade, Mockito.atLeast(1)).test();

		schedule.remove(jobId);
		WaitUtil.sleep(1500);
		Mockito.verify(facade, Mockito.atMost(2)).test();
	}

	static interface TestFacade {
		void test();
	}

	@Slf4j
	static class TestJob implements Runnable {
		TestFacade facade;

		public TestJob(TestFacade facade) {
			this.facade = facade;
		}

		@Override
		public void run() {
			log.info("Run");
			facade.test();
		}
	}

}
