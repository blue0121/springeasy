package io.jutil.springeasy.jdo.parser;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
public interface SqlMetadata {
	/**
	 * SQL: select * from [table] where id=?
	 *
	 * @return
	 */
	SqlItem getSelectById();

	/**
	 * SQL: select * from [table] where id in (?,?,?)
	 *
	 * @return
	 */
	SqlItem getSelectByIdList();

	/**
	 * SQL: insert into [table] (id,x) values (?,?)
	 *
	 * @return
	 */
	SqlItem getInsert();

	/**
	 * SQL: update [table] set x=? where id=?
	 *
	 * @return
	 */
	SqlItem getUpdateById();

	/**
	 * SQL: update [table] set x=? where id=? and version=?
	 *
	 * @return
	 */
	SqlItem getUpdateByIdAndVersion();

	/**
	 * SQL: delete from [table] where id=?
	 *
	 * @return
	 */
	SqlItem getDeleteById();

	/**
	 * SQL: delete from [table] where id in (?,?,?)
	 *
	 * @return
	 */
	SqlItem getDeleteByIdList();
}
