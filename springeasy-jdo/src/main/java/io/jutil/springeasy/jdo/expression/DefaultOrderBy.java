package io.jutil.springeasy.jdo.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-12
 */
class DefaultOrderBy implements OrderBy {
	private List<String> sqlList = new ArrayList<>();

	DefaultOrderBy() {
	}

	@Override
	public void add(String sql) {
		sqlList.add(sql);
	}

	@Override
	public String toString() {
		if (sqlList.isEmpty()) {
			return "";
		}

		StringBuilder sql = new StringBuilder();
		for (String obj : sqlList) {
			if (!sql.isEmpty()) {
				sql.append(",");
			}
			sql.append(obj);
		}
		return sql.toString();
	}

}
