package io.jutil.springeasy.mybatis.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * @author Jin Zheng
 * @since 2024-06-20
 */
public class BatchSqlSessionTemplate extends SqlSessionTemplate {

	public BatchSqlSessionTemplate(SqlSessionFactory sessionFactory) {
		super(sessionFactory, ExecutorType.BATCH);
	}
}
