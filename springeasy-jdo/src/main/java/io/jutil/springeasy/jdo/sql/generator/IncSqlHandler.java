package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.NumberUtil;
import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.jdo.exception.EntityFieldException;
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
public class IncSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var map = response.toParamMap();
		AssertUtil.notEmpty(map, "参数");

		var columnMap = config.getColumnMap();
		List<String> columnList = new ArrayList<>();

		for (var entry : map.entrySet()) {
			var column = columnMap.get(entry.getKey());
			if (column == null) {
				continue;
			}
			if (!NumberUtil.isNumber(column.getFieldOperation().getType())) {
				throw new EntityFieldException(entry.getKey(), "不是数字");
			}
			if (!NumberUtil.isNumber(entry.getValue().getClass())) {
				throw new EntityFieldException(entry.getKey(), "不是数字");
			}
			columnList.add(column.getColumnName() + "=" + column.getColumnName() + "+?");
			response.addName(entry.getKey());
		}

		var id = config.getIdMetadata();
		var whereId = id.getColumnName() + SqlConst.EQUAL_PLACEHOLDER;
		response.addName(id.getFieldName());

		var sql = String.format(SqlConst.UPDATE_TPL, config.getTableName(),
				StringUtil.join(columnList, SqlConst.SEPARATOR), whereId);
		response.setSql(sql);
	}
}
