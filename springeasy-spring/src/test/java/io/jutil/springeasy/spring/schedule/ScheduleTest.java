package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.core.util.WaitUtil;
import io.jutil.springeasy.spring.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@ActiveProfiles("schedule")
@SpringBootTest(classes = {Application.class, Config.class})
class ScheduleTest {
	@Autowired
	Job job;

	@Test
	void test() {
		WaitUtil.sleep(1500);
		Assertions.assertTrue(job.flag);
	}
}
