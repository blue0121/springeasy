package io.jutil.springeasy.mybatis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jin Zheng
 * @since 2024-06-03
 */
@SpringBootTest(classes = BaseTest.Application.class)
public interface BaseTest {

	@SpringBootApplication
	class Application {
	}
}
