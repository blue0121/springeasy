package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlConst;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class InsertSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var map = response.toParamMap();
		AssertUtil.notEmpty(map, "参数");

		if (!request.isDynamic()) {
			var sqlItem = config.getSqlMetadata().getInsert();
			response.setSql(sqlItem.getSql());
			sqlItem.getParameterNameList().forEach(response::addName);
			return;
		}

		var idMap = config.getIdMap();
		var columnMap = config.getColumnMap();
		var version = config.getVersionMetadata();
		List<String> columnList = new ArrayList<>();
		for (var entry : map.entrySet()) {
			var column = this.getColumnString(entry.getKey(), idMap, columnMap, version);
			columnList.add(column);
			response.addName(entry.getKey());
		}
		var sql = String.format(SqlConst.INSERT_TPL, config.getTableName(),
				StringUtil.join(columnList, SqlConst.SEPARATOR),
				StringUtil.repeat(SqlConst.PLACEHOLDER, columnList.size(), SqlConst.SEPARATOR));
		response.setSql(sql);
	}
}
