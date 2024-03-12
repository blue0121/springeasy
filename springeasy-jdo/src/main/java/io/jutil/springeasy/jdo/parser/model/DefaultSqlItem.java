package io.jutil.springeasy.jdo.parser.model;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jdo.parser.SqlItem;
import lombok.Getter;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-04
 */
@Getter
public class DefaultSqlItem implements SqlItem {
	private String sql;
	private List<String> parameterNameList;

	public DefaultSqlItem(String sql, List<String> parameterNameList) {
		this.sql = sql;
		this.setParameterNameList(parameterNameList);
	}

	@Override
	public String toString() {
		return String.format("SQL: %s, params: %s", sql, parameterNameList);
	}

	public void check() {
		AssertUtil.notEmpty(sql, "SQL");
		AssertUtil.notNull(parameterNameList, "参数名称列表");
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setParameterNameList(List<String> paramNameList) {
		if (paramNameList == null || paramNameList.isEmpty()) {
			this.parameterNameList = List.of();
		} else {
			this.parameterNameList = List.copyOf(paramNameList);
		}
	}
}
