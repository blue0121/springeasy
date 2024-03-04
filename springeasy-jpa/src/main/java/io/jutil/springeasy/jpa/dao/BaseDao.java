package io.jutil.springeasy.jpa.dao;

import io.jutil.springeasy.core.collection.Page;
import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.StringPlaceholder;
import io.jutil.springeasy.jpa.sql.DefaultExpression;
import io.jutil.springeasy.jpa.sql.Expression;
import io.jutil.springeasy.jpa.sql.HqlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jin Zheng
 * @since 2023-11-02
 */
@Slf4j
@SuppressWarnings("java:S119")
public abstract class BaseDao<T, ID extends Serializable> extends QueryDao {
	protected final Class<T> entityClazz;

	@SuppressWarnings("unchecked")
	protected BaseDao() {
		var clazz = AopUtils.getTargetClass(this);
		var types = ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments();
		this.entityClazz = (Class<T>) types[0];
	}

	public void insert(T entity) {
		entityManager.persist(entity);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public int updateBy(ID id, Map<String, ?> args) {
		if (args == null || args.isEmpty()) {
			log.warn("Args is empty");
			return 0;
		}

		Map<String, Object> map = new HashMap<>(args);
		map.put("id", id);
		var updateSet = HqlGenerator.updateSet(args);
		var hql = this.formatHql("update {entity} set {0} where id = :id", updateSet);
		return this.execute(hql, map);
	}

	public T getOne(ID id) {
		var list = this.getList(List.of(id));
		return list.isEmpty() ? null : list.get(0);
	}

	public T getOne(Consumer<Expression> f) {
		var expression = new DefaultExpression();
		if (f != null) {
			f.accept(expression);
		}
		var snippet = expression.toHqlSnippet();
		var args = expression.toArgs();

		var select = this.select(snippet);
		return this.querySingle(entityClazz, select, args);
	}

	public List<T> getList(Collection<ID> idList) {
		var map = Map.of("id", idList);
		var hql = this.select("where e.id in (:id)");
		return this.queryList(entityClazz, hql, map);
	}

	public int deleteList(Collection<ID> idList) {
		var hql = this.formatHql("delete from {entity} where id in (?1)");
		return this.execute(hql, idList);
	}

	public int deleteAll() {
		var hql = this.formatHql("delete from {entity}");
		return this.execute(hql);
	}

	public int deleteBy(Map<String, ?> args) {
		if (args == null || args.isEmpty()) {
			log.warn("Args is empty");
			return 0;
		}
		var where = HqlGenerator.where(args);
		var hql = this.formatHql("delete from {entity} {0}", where);
		return this.execute(hql, args);
	}

	public List<T> listAll() {
		var hql = this.select("");
		return this.queryList(entityClazz, hql);
	}

	public List<T> list(Consumer<Expression> f) {
		var expression = new DefaultExpression();
		if (f != null) {
			f.accept(expression);
		}
		var snippet = expression.toHqlSnippet();
		var args = expression.toArgs();

		var select = this.select(snippet);
		return this.queryList(entityClazz, select, args);
	}

	public int count() {
		var selectCount = this.selectCount("");
		var count = this.querySingle(Long.class, selectCount);
		return count.intValue();
	}

	public int count(Consumer<Expression> f) {
		var expression = new DefaultExpression();
		if (f != null) {
			f.accept(expression);
		}

		var selectCount = this.selectCount(expression.toHqlWhere());
		var count = this.querySingle(Long.class, selectCount, expression.toArgs());
		return count.intValue();
	}

	private int count(String where, Map<String, ?> args) {
		var selectCount = this.selectCount(where);
		var count = this.querySingle(Long.class, selectCount, args);
		return count.intValue();
	}

	public Page listPage(Page page, Consumer<Expression> f) {
		AssertUtil.notNull(page, "Page");
		var expression = new DefaultExpression();
		if (f != null) {
			f.accept(expression);
		}
		var args = expression.toArgs();

		page.setTotal(this.count(expression.toHqlWhere(), args));
		var list = this.queryPage(entityClazz, this.select(expression.toHqlSnippet()),
				page.getOffset(), page.getPageSize(), args);
		page.setContents(list);
		return page;
	}

	protected String selectCount(String where) {
		return this.formatHql("select count(*) from {entity} e {0}", where);
	}

	protected String select(String where) {
		return this.formatHql("from {entity} e {0}", where);
	}

	public void flush() {
		entityManager.flush();
	}

	public void detach(T entity) {
		entityManager.detach(entity);
	}

	public void refresh(Object obj) {
		entityManager.refresh(obj);
	}

	protected String formatHql(String tpl, String...args) {
		Map<String, String> map = new HashMap<>();
		var holder = new StringPlaceholder();
		map.put("entity", entityClazz.getSimpleName());
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				map.put(String.valueOf(i), args[i]);
			}
		}
		return holder.template(tpl, map);
	}

}
