package io.jutil.springeasy.spring.schedule;

import io.jutil.springeasy.spring.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Jin Zheng
 * @since 2023-08-12
 */
@ActiveProfiles("executor")
@SpringBootTest(classes = Application.class)
class ExecutorTest {
	@Autowired
	@Qualifier("executor1")
	ExecutorService executor1;

	@Autowired
	@Qualifier("executor2")
	ExecutorService executor2;

	@CsvSource({"executor1,SynchronousQueue", "executor2,LinkedBlockingQueue"})
	@ParameterizedTest
	void test(String id, String queueName) {
		var executor = switch (id) {
			case "executor1" -> executor1;
			default -> executor2;
		};
		if (executor instanceof ThreadPoolExecutor t) {
			Assertions.assertEquals(1, t.getCorePoolSize());
			Assertions.assertEquals(10, t.getMaximumPoolSize());
			var queue = t.getQueue();
			Assertions.assertEquals(queueName, queue.getClass().getSimpleName());
		} else {
			Assertions.fail();
		}
	}
}
