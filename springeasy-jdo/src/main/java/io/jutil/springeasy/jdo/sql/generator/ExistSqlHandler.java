package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.jdo.exception.JdoException;
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
public class ExistSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		List<?> args = request.getArgs() == null ? List.of() : request.getArgs();
		var map = response.toParamMap();
		var config = request.getMetadata();

		List<String> columnList = new ArrayList<>();
		if (!args.isEmpty()) {
			var columnMap = config.getColumnMap();
			for (var arg : args) {
				var strArg = arg.toString();
				var whereColumn = this.getColumnString(strArg, null, columnMap, null);
				columnList.add(whereColumn + SqlConst.EQUAL_PLACEHOLDER);
				response.addName(strArg);
			}
		}

		String op = args.isEmpty() ? SqlConst.EQUAL_PLACEHOLDER : SqlConst.NOT_EQUAL_PLACEHOLDER;
		var idMap = config.getIdMap();
		for (var entry : idMap.entrySet()) {
			var id = entry.getValue();
			var value = map.get(entry.getKey());
			if (!this.isEmpty(value)) {
				columnList.add(id.getColumnName() + op);
				response.addName(id.getFieldName());
			}
		}
		if (columnList.isEmpty()) {
			throw new JdoException("@Column 或 @Id 不能为空");
		}
		var sql = String.format(SqlConst.COUNT_TPL, config.getTableName(), StringUtil.join(columnList, SqlConst.AND));
		response.setSql(sql);
	}
}
