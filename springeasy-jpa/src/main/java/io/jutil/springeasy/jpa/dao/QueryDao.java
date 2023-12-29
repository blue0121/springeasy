package io.jutil.springeasy.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2023-11-05
 */
public abstract class QueryDao {
	private static final int TUPLE_INDEX = 0;

	@PersistenceContext
	protected EntityManager entityManager;


	protected int execute(String hql, Object...args) {
		var query = entityManager.createQuery(hql);
		this.setQueryParam(query, args);
		return query.executeUpdate();
	}

	protected int execute(String hql, Map<String, ?> args) {
		var query = entityManager.createQuery(hql);
		this.setQueryParam(query, args);
		return query.executeUpdate();
	}

	protected <T> T querySingle(Class<T> clazz, String hql, Object...args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		return this.getSingleResult(clazz, query);
	}

	protected <T> List<T> queryList(Class<T> clazz, String hql, Object...args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		return this.getResultList(clazz, query);
	}

	protected <T> List<T> queryPage(Class<T> clazz, String hql,
								 int offset, int pageSize,
								 Object...args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return this.getResultList(clazz, query);
	}

	protected <T> T querySingle(Class<T> clazz, String hql, Map<String, ?> args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		return this.getSingleResult(clazz, query);
	}

	protected <T> List<T> queryList(Class<T> clazz, String hql, Map<String, ?> args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		return this.getResultList(clazz, query);
	}

	protected <T> List<T> queryPage(Class<T> clazz, String hql,
								 int offset, int pageSize,
								 Map<String, ?> args) {
		var query = entityManager.createQuery(hql, Tuple.class);
		this.setQueryParam(query, args);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return this.getResultList(clazz, query);
	}

	protected <T> List<T> getResultList(Class<T> clazz, TypedQuery<Tuple> query) {
		var tupleList = query.getResultList();
		List<T> entityList = new ArrayList<>();
		for (var tuple : tupleList) {
			var entity = this.toEntity(clazz, tuple);
			entityList.add(entity);
		}
		return entityList;
	}

	protected <T> T getSingleResult(Class<T> clazz, TypedQuery<Tuple> query) {
		query.setMaxResults(1);
		var tupleList = query.getResultList();
		if (tupleList.isEmpty()) {
			return null;
		}
		var tuple = tupleList.get(0);
		return this.toEntity(clazz, tuple);
	}

	protected <T> T toEntity(Class<T> clazz, Tuple tuple) {
		if (Number.class.isAssignableFrom(clazz)) {
			return tuple.get(TUPLE_INDEX, clazz);
		}
		if (clazz.isEnum()) {
			return tuple.get(TUPLE_INDEX, clazz);
		}
		if (clazz == String.class) {
			return tuple.get(TUPLE_INDEX, clazz);
		}
		return this.extractFromTuple(clazz, tuple);
	}

	protected <T> T extractFromTuple(Class<T> clazz, Tuple tuple) {
		return tuple.get(TUPLE_INDEX, clazz);
	}

	protected void setQueryParam(Query query, Object...args) {
		if (args == null || args.length == 0) {
			return;
		}

		int i = 1;
		for (var arg : args) {
			query.setParameter(i, arg);
			++i;
		}
	}

	protected void setQueryParam(Query query, Map<String, ?> args) {
		if (args == null || args.isEmpty()) {
			return;
		}

		for (var entry : args.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}
