package io.jutil.springeasy.jdo.dialect;

import io.jutil.springeasy.jdo.annotation.LockModeType;

/**
 * @author Jin Zheng
 * @since 2024-03-08
 */
public interface Dialect {

	String escape();

	/**
	 * 把表名或字段加上转义字符
	 *
	 * @param key 表名或字段
	 * @return 转义后的表名或字段
	 */
	String escape(String key);

	/**
	 * 把 SQL 语句加上分页功能
	 *
	 * @param sql   SQL语句
	 * @param start 起始行号
	 * @param size  最大记数数
	 * @return 带分页的 SQL 语句
	 */
	String page(String sql, int start, int size);

	/**
	 * 在 SQL 语句上加上锁
	 *
	 * @param sql  SQL语句
	 * @param type 锁类型
	 * @return 带锁的 SQL 语句
	 */
	String lock(String sql, LockModeType type);

	/**
	 * 把 SQL 语句限制第1条记录
	 *
	 * @param sql SQL语句
	 * @return 限制第1条记录的 SQL 语句
	 */
	default String getOne(String sql) {
		return this.page(sql, 0, 1);
	}

}
