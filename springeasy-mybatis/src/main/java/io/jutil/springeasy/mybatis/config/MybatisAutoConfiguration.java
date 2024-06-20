package io.jutil.springeasy.mybatis.config;

import io.jutil.springeasy.mybatis.dao.BatchSqlSessionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2024-06-13
 */
@Slf4j
@Configuration
public class MybatisAutoConfiguration {

	@Bean
	public BatchSqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory factory) {
		var template = new BatchSqlSessionTemplate(factory);
		log.info("Create SqlSessionTemplate, executorType: BATCH");
		return template;
	}
}
