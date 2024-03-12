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
public class GetFieldSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var map = response.toParamMap();
		var field = request.getField();
		AssertUtil.notEmpty(map, "参数");

		var columnMap = config.getColumnMap();
		var idMap = config.getIdMap();
		var version = config.getVersionMetadata();
		List<String> columnList = new ArrayList<>();

		String fieldColumn = this.getColumnString(field, idMap, columnMap, version);
		for (var entry : map.entrySet()) {
			String whereColumn = this.getColumnString(entry.getKey(), idMap, columnMap, version);
			columnList.add(whereColumn + SqlConst.EQUAL_PLACEHOLDER);
			response.addName(entry.getKey());
		}
		var sql = String.format(SqlConst.GET_TPL, fieldColumn, config.getTableName(), StringUtil.join(columnList, SqlConst.AND));
		response.setSql(sql);
	}
}
