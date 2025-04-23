package io.jutil.springeasy.spring.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2025-04-21
 */
@Configuration
public class Config {
	@Bean
	public Job job() {
		return new Job();
	}
}
