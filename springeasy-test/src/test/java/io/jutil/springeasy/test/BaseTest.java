package io.jutil.springeasy.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2025-07-12
 */
@SpringBootTest(classes = BaseTest.Application.class)
public interface BaseTest {


	@SpringBootApplication
	class Application {
	}
}
