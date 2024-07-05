package io.jutil.springeasy.mybatis.dao;

import io.jutil.springeasy.mybatis.entity.LongIdEntity;
import io.jutil.springeasy.mybatis.id.LongIdGenerator;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-06-13
 */
public abstract class BaseDao<T extends LongIdEntity> {
	private static final String INSERT_NAME = "insert";
	private static final String UPDATE_NAME = "update";
	private static final int BATCH_SIZE = 100;

	@Autowired
	@SuppressWarnings("java:S6813")
	private SqlSessionFactory sessionFactory;

	public int insertList(Class<?> mapper, List<T> list) {
		return this.insertList(mapper, INSERT_NAME, list, BATCH_SIZE);
	}

	public int insertList(Class<?> mapper, String name, List<T> list, int batchSize) {
		var statement = mapper.getName() + "." + name;
		int i = 1;
		try (var sqlSession = sessionFactory.openSession(ExecutorType.BATCH)) {
			for (var entity : list) {
				entity.setId(LongIdGenerator.nextId());
				sqlSession.insert(statement, entity);
				if (i % batchSize == 0 || i == list.size()) {
					sqlSession.flushStatements();
				}
			}
			sqlSession.commit();
		}
		return list.size();
	}

	public int updateList(Class<?> mapper, List<T> list) {
		return this.updateList(mapper, UPDATE_NAME, list, BATCH_SIZE);
	}

	public int updateList(Class<?> mapper, String name, List<T> list, int batchSize) {
		var statement = mapper.getName() + "." + name;
		int i = 1;
		try (var sqlSession = sessionFactory.openSession(ExecutorType.BATCH)) {
			for (var entity : list) {
				sqlSession.update(statement, entity);
				if (i % batchSize == 0 || i == list.size()) {
					sqlSession.flushStatements();
				}
			}
			sqlSession.commit();
		}
		return list.size();
	}
}
