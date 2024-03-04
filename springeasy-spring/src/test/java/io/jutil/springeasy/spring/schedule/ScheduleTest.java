package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.schedule.ScheduleContext;
import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.spring.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@ActiveProfiles("schedule")
@SpringBootTest(classes = {Application.class, ScheduleTest.Config.class})
class ScheduleTest {
	@Autowired
	Job job;

	@Test
	void test() {
		WaitUtil.sleep(1500);
		Assertions.assertTrue(job.flag);
	}

	@Configuration
	static class Config {
		@Bean
		public Job job() {
			return new Job();
		}
	}

	@Slf4j
	static class Job implements SpringScheduleJob {
		boolean flag = false;

		@Override
		public void run(ScheduleContext ctx) {
			log.info("Run springScheduleJob, id: {}, cron: {}", ctx.getId(), ctx.getCron());
			flag = true;
		}

		@Override
		public String getId() {
			return "job";
		}
	}
}
