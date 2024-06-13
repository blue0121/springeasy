package io.jutil.springeasy.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
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
	public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory factory) {
		var template = new SqlSessionTemplate(factory, ExecutorType.BATCH);
		log.info("Create SqlSessionTemplate, executorType: BATCH");
		return template;
	}
}
