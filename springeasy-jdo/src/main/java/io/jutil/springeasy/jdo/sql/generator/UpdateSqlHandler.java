package io.jutil.springeasy.jdo.sql.generator;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.core.util.StringUtil;
import io.jutil.springeasy.jdo.exception.JdoException;
import io.jutil.springeasy.jdo.sql.AbstractSqlHandler;
import io.jutil.springeasy.jdo.sql.SqlConst;
import io.jutil.springeasy.jdo.sql.SqlRequest;
import io.jutil.springeasy.jdo.sql.SqlResponse;
import io.jutil.springeasy.jdo.util.VersionUtil;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@NoArgsConstructor
public class UpdateSqlHandler extends AbstractSqlHandler {

	@Override
	public void handle(SqlRequest request, SqlResponse response) {
		var config = request.getMetadata();
		var map = response.toParamMap();
		AssertUtil.notEmpty(map, "参数");

		if (!request.isDynamic()) {
			var sql = config.getSqlMetadata();
			var sqlItem = VersionUtil.isForce(config) ? sql.getUpdateByIdAndVersion() : sql.getUpdateById();
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
			if (columnMap.containsKey(entry.getKey())) {
				columnList.add(column + SqlConst.EQUAL_PLACEHOLDER);
				response.addName(entry.getKey());
			} else if (version != null && version.getFieldName().equals(entry.getKey())) {
				columnList.add(column + SqlConst.EQUAL + column + "+1");
			}
		}
		if (columnList.isEmpty()) {
			throw new JdoException("@Column 不能为空");
		}

		List<String> idList = new ArrayList<>();
		for (var entry : map.entrySet()) {
			var id = this.getColumnString(entry.getKey(), idMap, columnMap, version);
			if (!columnMap.containsKey(entry.getKey())) {
				idList.add(id + SqlConst.EQUAL_PLACEHOLDER);
				response.addName(entry.getKey());
			}
		}
		if (idList.isEmpty()) {
			throw new JdoException("@Id 不能为空");
		}

		var sql = String.format(SqlConst.UPDATE_TPL, config.getTableName(),
				StringUtil.join(columnList, SqlConst.SEPARATOR),
				StringUtil.join(idList, SqlConst.AND));
		response.setSql(sql);
	}
}
