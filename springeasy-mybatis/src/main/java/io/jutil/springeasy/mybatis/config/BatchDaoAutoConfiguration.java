package io.jutil.springeasy.mybatis.config;

import io.jutil.springeasy.mybatis.dao.BatchDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Jin Zheng
 * @since 2025-12-25
 */
@Configuration
@ConditionalOnClass(NamedParameterJdbcTemplate.class)
public class BatchDaoAutoConfiguration {

	@Bean
	public BatchDao batchDao(NamedParameterJdbcTemplate jdbcTemplate) {
		return new BatchDao(jdbcTemplate);
	}
}
